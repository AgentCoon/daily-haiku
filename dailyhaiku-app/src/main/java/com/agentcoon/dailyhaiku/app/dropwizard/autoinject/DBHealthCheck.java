package com.agentcoon.dailyhaiku.app.dropwizard.autoinject;

import com.google.common.util.concurrent.MoreExecutors;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;
import io.dropwizard.db.TimeBoundHealthCheck;
import io.dropwizard.util.Duration;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.inject.Inject;

/**
 * Guice injectable version of DBIHealthCheck.
 *
 * See
 * https://github.com/dropwizard/dropwizard/blob/master/dropwizard-jdbi/src/main
 * /java/io/dropwizard/jdbi/DBIHealthCheck.java
 */
public class DBHealthCheck extends InjectableHealthCheck {

    private final DBI dbi;
    private final String validationQuery;
    private final TimeBoundHealthCheck timeBoundHealthCheck;

    @Inject
    public DBHealthCheck(DBI dbi) {

        this.dbi = dbi;
        this.validationQuery = "SELECT 1";
        this.timeBoundHealthCheck = new TimeBoundHealthCheck(MoreExecutors.newDirectExecutorService(), Duration.seconds(0));
    }

    @Override
    public String getName() {
        return "db health check";
    }

    @Override
    protected Result check() throws Exception {
        return timeBoundHealthCheck.check(() -> {
            try (Handle handle = dbi.open()) {
                handle.execute(validationQuery);
                return Result.healthy();
            }
        });
    }

}