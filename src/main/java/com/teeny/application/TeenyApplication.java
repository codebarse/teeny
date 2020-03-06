package com.teeny.application;

import com.teeny.configuration.ApplicationConfiguration;
import com.teeny.dao.TeenyUrlDAO;
import com.teeny.models.TeenyUrl;
import com.teeny.resource.ApplicationResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TeenyApplication extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new TeenyApplication().run(args);
    }

    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<ApplicationConfiguration> hibernateBundle
            = new HibernateBundle<ApplicationConfiguration>(TeenyUrl.class) {

        @Override
        public DataSourceFactory getDataSourceFactory(ApplicationConfiguration applicationConfiguration) {
            return applicationConfiguration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(
            final Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(ApplicationConfiguration applicationConfiguration, Environment environment) throws Exception {
        final TeenyUrlDAO teenyDao = new TeenyUrlDAO(hibernateBundle.getSessionFactory());
        final ApplicationResource resource = new ApplicationResource(
                applicationConfiguration.getTemplate(),
                applicationConfiguration.getDefaultName(),
                teenyDao
        );

        environment.jersey().register(resource);
    }
}
