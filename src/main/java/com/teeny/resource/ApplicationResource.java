package com.teeny.resource;

import com.codahale.metrics.annotation.Timed;
import com.teeny.application.Saying;
import com.teeny.dao.TeenyUrlDAO;
import com.teeny.model.TeenyUrl;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/teeny")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
	
	private TeenyUrlDAO teenyDAO;
	
	public ApplicationResource(TeenyUrlDAO teenyDAO) {
		this.teenyDAO = teenyDAO;
	}
	
	@GET
	@Path("/noHello")
	public Saying dontSayHello() {
		return new Saying(1, "Fish off!");
	}
	
	/**
	 * Returns all
	 * teeny url stored in the database.
	 *
	 * @return list of TeenyUrl of all urls stored in the database.
	 */
	@GET
	@Path("/all")
	@Timed
	@UnitOfWork
	public List<TeenyUrl> getAllUrls() {
		return teenyDAO.findAll();
	}
	
	/**
	 * Method looks for an TeenyUrl by id.
	 *
	 * @param id the id of an TeenyUrl we are looking for.
	 * @return Optional containing the found TeenyUrl or an empty Optional
	 * otherwise.
	 */
	@GET
	@Path("/{id}")
	@UnitOfWork
	public Optional<TeenyUrl> findById(@PathParam("id") Long id) {
		return teenyDAO.findById(id);
	}

//	@POST
//	@Path("/create")
//	@UnitOfWork
//	public Optional<TeenyUrl> createTeenyUrl(@)
}
