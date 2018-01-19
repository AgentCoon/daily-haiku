package com.agentcoon.dailyhaiku.inttest;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.validation.Validators;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.logging.SLF4JLog;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

import java.io.IOException;

public class EmbeddedDb extends ExternalResource {

    private static DBI dbi = null;

    @Override
    protected void before() throws Throwable {
        setupDb();
        clearDb();
    }

    private static synchronized void setupDb() throws IOException {

        if (dbi != null) {
            return;
        }

        // start Postgres
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();
        final PostgresConfig config = PostgresConfig.defaultWithDbName("test", "user", "password");
        PostgresExecutable exec = runtime.prepare(config);
        exec.start();

        String url = String.format("jdbc:postgresql://%s:%s/%s", config.net().host(), config.net().port(), config.storage().dbName());

        // create the schema
        Flyway flyway = new Flyway();
        flyway.setDataSource(url, "user", "password");
        flyway.migrate();

        // initiate JDBI to enable repositories

        Environment env = new Environment("test", Jackson.newObjectMapper(), Validators.newValidator(),
                new MetricRegistry(), null);

        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.postgresql.Driver");
        dataSourceFactory.setUrl(url);
        dataSourceFactory.setUser("user");
        dataSourceFactory.setPassword("password");
        dataSourceFactory.setInitialSize(1);
        dbi = new DBIFactory().build(env, dataSourceFactory, "test");
        dbi.setSQLLog(new SLF4JLog());
    }

    /**
     * Get an instance of a Repository
     *
     * @param clazz
     * @return
     */
    public <T> T repository(Class<T> clazz) {

        return dbi.onDemand(clazz);
    }

    private void clearDb() {

        Handle h = dbi.open();
        h.execute("TRUNCATE TABLE haiku CASCADE");
        h.close();
    }
}
