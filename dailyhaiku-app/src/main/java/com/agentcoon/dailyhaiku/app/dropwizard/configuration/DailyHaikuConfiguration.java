package com.agentcoon.dailyhaiku.app.dropwizard.configuration;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DailyHaikuConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database;

    @NotNull
    private Boolean startEmbeddedDB;

    @NotNull
    private Boolean allowCORS;

    public DataSourceFactory getDatabase() {
        return database;
    }

    public Boolean getStartEmbeddedDB() {
        return startEmbeddedDB;
    }

    public Boolean getAllowCORS() {
        return allowCORS;
    }
}
