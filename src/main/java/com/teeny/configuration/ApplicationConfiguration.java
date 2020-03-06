package com.teeny.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ApplicationConfiguration extends Configuration {
	@NotEmpty
	private String template;
	
	@NotEmpty
	private String defaultName = "Stranger";
	
	@JsonProperty
	public String getTemplate() {
		return template;
	}
	
	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}
	
	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}
	
	@JsonProperty
	public void setDefaultName(String name) {
		this.defaultName = name;
	}

	/**
	 * A factory used to connect to a relational database management system.
	 * Factories are used by Dropwizard to group together related configuration
	 * parameters such as database connection driver, URI, password etc.
	 */
	@NotNull
	@Valid
	private DataSourceFactory dataSourceFactory = new DataSourceFactory();

	/**
	 * A getter for the database factory.
	 *
	 * @return An instance of database factory deserialized from the
	 * configuration file passed as a command-line argument to the application.
	 */
	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}
}
