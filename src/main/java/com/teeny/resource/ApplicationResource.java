package com.teeny.resource;

import com.codahale.metrics.annotation.Timed;
import com.teeny.response.CreateResponse;
import com.teeny.application.Saying;
import com.teeny.dao.TeenyUrlDAO;
import com.teeny.model.TeenyUrl;
import com.teeny.request.CreateRequest;
import com.teeny.utils.Utils;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/teeny")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
	
	private TeenyUrlDAO teenyUrlDAO;
	
	public ApplicationResource(TeenyUrlDAO teenyUrlDAO) {
		this.teenyUrlDAO = teenyUrlDAO;
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
		return teenyUrlDAO.findAll();
	}
	
	/**
	 * Method looks for a TeenyUrl by id.
	 *
	 * @param teenyUrl
	 * @return Optional containing the found TeenyUrl or an empty Optional
	 * otherwise.
	 */
	@GET
	@Path("/{teenyUrl}")
	@UnitOfWork
	public Optional<TeenyUrl> findById(@PathParam("teenyUrl") String teenyUrl) {
		long id = Utils.teenyUrlToId(teenyUrl);
		return teenyUrlDAO.findById(id);
	}
	
	@POST
	@Path("/create")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	public Optional<CreateResponse> createTeenyUrl(CreateRequest request) {
		//Check if the url is present in DB
		List<TeenyUrl> byUrl = teenyUrlDAO.findByUrl(request.getUrl());
		//If the url is in the DB, then use the existing ID to create teenyUrl
		if(byUrl.size() > 0) {
			CreateResponse response = new CreateResponse();
			long id = byUrl.get(0).getId();
			response.setTeenyUrl(Utils.idToTeenyUrl(id));
			return Optional.of(response);
		}

		//Inserting Url in the DB to get a unique ID
		TeenyUrl dao = new TeenyUrl();
		dao.setUrl(request.getUrl());
		Optional<TeenyUrl> urlWithId = teenyUrlDAO.insertUrl(dao);

		//Converting ID to a teeny encoding and returning
		long id = urlWithId.get().getId();
		String teenyUrl = Utils.idToTeenyUrl(id);
		CreateResponse response = new CreateResponse();
		response.setTeenyUrl(teenyUrl);
		return Optional.of(response);
	}

}
