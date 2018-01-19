package com.agentcoon.dailyhaiku.app.dropwizard;

import com.agentcoon.dailyhaiku.app.dropwizard.configuration.DailyHaikuConfiguration;
import com.agentcoon.dailyhaiku.domain.HaikuRepository;
import com.agentcoon.dailyhaiku.infrastructure.jdbi.haiku.JdbiHaikuRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import javax.inject.Singleton;

/**
 * JDBI - database access
 */
public class DBAccessModule extends AbstractModule {

    @Provides
    @Singleton
    public DBI prepareJdbi(Environment environment, DailyHaikuConfiguration configuration) {

        DBIFactory factory = new DBIFactory();
        return factory.build(environment, configuration.getDatabase(), "postgresql");
    }

    @Provides
    @Singleton
    public HaikuRepository prepareHaikuRepository(DBI jdbi) {
        return jdbi.onDemand(JdbiHaikuRepository.class);
    }

    @Override
    protected void configure() {}
}