package com.teeny.application;

import com.teeny.configuration.ApplicationConfiguration;
import com.teeny.dao.TeenyUrlDAO;
import com.teeny.model.TeenyUrl;
import com.teeny.resource.ApplicationResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class TeenyApplication extends Application<ApplicationConfiguration> {
	
	/**
	 * Hibernate bundle.
	 */
	private final HibernateBundle<ApplicationConfiguration> hibernateBundle
			= new HibernateBundle<ApplicationConfiguration>(TeenyUrl.class) {
		
		@Override
		public PooledDataSourceFactory getDataSourceFactory(ApplicationConfiguration applicationConfiguration) {
			return applicationConfiguration.getDataSourceFactory();
		}
	};
	
	public static void main(String[] args) throws Exception {
		new TeenyApplication().run(args);
	}
	
	@Override
	public void initialize(
			final Bootstrap<ApplicationConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}
	
	@Override
	public void run(ApplicationConfiguration applicationConfiguration, Environment environment) throws Exception {
		final TeenyUrlDAO teenyDao = new TeenyUrlDAO(hibernateBundle.getSessionFactory());
		final ApplicationResource resource = new ApplicationResource(
				teenyDao
		);
		// Enable CORS headers
		final FilterRegistration.Dynamic cors =
				environment.servlets().addFilter("CORS", CrossOriginFilter.class);

		// Configure CORS parameters
		cors.setInitParameter("allowedOrigins", "*");
		cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		
		environment.jersey().register(resource);
	}
}
