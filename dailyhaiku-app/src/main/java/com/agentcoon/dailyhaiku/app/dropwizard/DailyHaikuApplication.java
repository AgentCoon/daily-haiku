package com.agentcoon.dailyhaiku.app.dropwizard;

import com.agentcoon.dailyhaiku.app.dropwizard.configuration.DailyHaikuConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class DailyHaikuApplication extends Application<DailyHaikuConfiguration> {

    private GuiceBundle<DailyHaikuConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new DailyHaikuApplication().run(args);
    }

    @Override
    public String getName() {
        return "daily-haiku";
    }

    @Override
    public void initialize(Bootstrap<DailyHaikuConfiguration> bootstrap) {

        // embedded db - enabled for testing and local dev
        bootstrap.addBundle(new EmbeddedPostgresBundle());

        // flyway - creates and updates the DB schema
        bootstrap.addBundle(new DBMigrationsBundle());

        guiceBundle = GuiceBundle.<DailyHaikuConfiguration>newBuilder()
                .addModule(new DBAccessModule())
                .enableAutoConfig("com.agentcoon.dailyhaiku.domain",
                        "com.agentcoon.dailyhaiku.infrastructure",
                        "com.agentcoon.dailyhaiku.rest",
                        "com.agentcoon.dailyhaiku.app.dropwizard.autoinject")
                .setConfigClass(DailyHaikuConfiguration.class)
                .build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);

        bootstrap.getObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        /**
         * live api documentation - Mulesoft interactive api-console populated
         * from our RAML API definition
         */
        bootstrap.addBundle(new AssetsBundle("/raml", "/raml", null, "raml"));
        bootstrap.addBundle(new AssetsBundle("/api/", "/api", "index.html"));
        bootstrap.addBundle(new AssetsBundle("/api/styles", "/styles", null, "styles"));
        bootstrap.addBundle(new AssetsBundle("/api/scripts", "/scripts", null, "scripts"));
        bootstrap.addBundle(new AssetsBundle("/api/fonts", "/fonts", null, "fonts"));
    }

    @Override
    public void run(DailyHaikuConfiguration config, Environment env) throws Exception {

        if (config.getAllowCORS()) {
            final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

            cors.setInitParameter("allowedOrigins", "*");
            cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
            cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

            cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        }
    }
}
