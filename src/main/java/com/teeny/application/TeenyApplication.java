package com.teeny.application;

import com.teeny.configuration.ApplicationConfiguration;
import com.teeny.resource.ApplicationResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TeenyApplication extends Application<ApplicationConfiguration> {
	
	public static void main(String[] args) throws Exception {
		new TeenyApplication().run(args);
	}
	
	public void run(ApplicationConfiguration applicationConfiguration, Environment environment) throws Exception {
		final ApplicationResource resource = new ApplicationResource(
				applicationConfiguration.getTemplate(),
				applicationConfiguration.getDefaultName()
		);
		
		environment.jersey().register(resource);
	}
}
