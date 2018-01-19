package com.agentcoon.dailyhaiku.app.dropwizard;

import com.agentcoon.dailyhaiku.app.dropwizard.configuration.DailyHaikuConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

/**
 * Testing and Local development only
 * <p>
 * enabled/disabled via the configuration
 * <p>
 * Bootstraps an embedded instance of PostgreSQL.
 * Data is not saved between restarts.
 */
public class EmbeddedPostgresBundle implements ConfiguredBundle<DailyHaikuConfiguration> {

    private static final String LISTEN_ADDR = "127.0.0.1";
    private static final int LISTEN_PORT = 5433;
    private static final String DB_PASSWORD = "password";
    private static final String DB_USER = "user";
    private static final String DB_NAME = "test";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedPostgresBundle.class);

    @Override
    public synchronized void run(DailyHaikuConfiguration configuration, Environment environment) throws Exception {
        if (!configuration.getStartEmbeddedDB()) {
            return;
        }

        // start Postgres
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();

        PostgresConfig config = new PostgresConfig(Version.Main.PRODUCTION,
                new AbstractPostgresConfig.Net(LISTEN_ADDR, LISTEN_PORT),
                new AbstractPostgresConfig.Storage(DB_NAME),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials(DB_USER, DB_PASSWORD));

        PostgresExecutable exec = runtime.prepare(config);

        LOGGER.info("embedded postgres - starting");
        exec.start();
        LOGGER.info("embedded postgres - started");
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // no further initialisation required
    }
}