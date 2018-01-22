package com.agentcoon.dailyhaiku.app.dropwizard;

import com.agentcoon.dailyhaiku.app.dropwizard.configuration.DailyHaikuConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;

/**
 * Migrates the DB schema to the latest version on application startup
 */
public class DBMigrationsBundle implements ConfiguredBundle<DailyHaikuConfiguration> {

    @Override
    public void run(DailyHaikuConfiguration configuration, Environment environment) {
        Flyway flyway = new Flyway();

        flyway.setDataSource(configuration.getDatabase().getUrl(), configuration.getDatabase().getUser(),
                configuration.getDatabase().getPassword());

        flyway.migrate();
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // no further initialisation required
    }
}
