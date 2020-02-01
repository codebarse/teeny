package com.teeny.resource;

import com.codahale.metrics.annotation.Timed;
import com.teeny.application.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
	
	private final String template;
	private final String defaultName;
	
	public ApplicationResource(String template, String defaultName) {
		this.template = template;
		this.defaultName = defaultName;
	}
	
	@GET
	@Timed
	@Path("/hello")
	public Saying sayHello(@QueryParam("name") Optional<String> name) {
		final String value = String.format(template, name.orElse(defaultName));
		return new Saying(1, value);
		
	}
	
	@GET
	@Path("/noHello")
	public Saying dontSayHello() {
		return new Saying(1, "Fish off!");
	}
}
