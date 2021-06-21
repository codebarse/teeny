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

		//Normal Url shortening
		if(request.getCustomKey().trim().isEmpty()) {
			return createNormalTeenyUrl(request);
		}

		//Url shortening with custom key
		else {
			return createCustomTeenyUrl(request);
		}
	}


	private Optional<CreateResponse> createCustomTeenyUrl(CreateRequest request) {

		//Check if key is present in DB
		Boolean isKeyAvailable = isKeyAvailable(request.getCustomKey());
		if(!isKeyAvailable) return null;

		long timestamp = System.currentTimeMillis();
		//Inserting Url in the DB
		TeenyUrl dao = new TeenyUrl();
		dao.setKey(request.getCustomKey());
		dao.setUrl(request.getUrl());
		dao.setCreatedOn(timestamp);
		Optional<TeenyUrl> teenyWithId = teenyUrlDAO.insertTeenyUrl(dao);

		//Return key from success response
		CreateResponse response = new CreateResponse();
		response.setTeenyUrl(teenyWithId.get().getKey());
		return Optional.of(response);
	}

	private Optional<CreateResponse> createNormalTeenyUrl(CreateRequest request) {

		//Check if the url is present in DB
		List<TeenyUrl> byUrl = teenyUrlDAO.findByUrl(request.getUrl());
		if(byUrl.size() > 0) {

			//Check whether the teenyurl was not a custom one
			Boolean isCustomUrl = true;
			for(TeenyUrl teenyUrl : byUrl) {
				if(Utils.teenyUrlToId(teenyUrl.getKey()) == teenyUrl.getId())isCustomUrl = false;

				//If it is not a custom Url then reuse the existing key
				if(!isCustomUrl) {
					String existingKey = teenyUrl.getKey();
					CreateResponse response = new CreateResponse();
					response.setTeenyUrl(existingKey);
					return Optional.of(response);
				}
			}
		}

		//Inserting Url in the DB to get a unique ID
		long timestamp = System.currentTimeMillis();
		TeenyUrl dao = new TeenyUrl();
		dao.setUrl(request.getUrl());
		dao.setCreatedOn(timestamp);
		Optional<TeenyUrl> urlWithId = teenyUrlDAO.insertTeenyUrl(dao);

		//Converting ID to a teeny key
		long id = urlWithId.get().getId();
		String teenyUrl = Utils.idToTeenyUrl(id);

		//Persisting teeny key in the DB
		dao.setKey(teenyUrl);
		teenyUrlDAO.insertTeenyUrl(dao);

		CreateResponse response = new CreateResponse();
		response.setTeenyUrl(teenyUrl);
		return Optional.of(response);
	}

	/**
	 * Checks whether a given key is already used in the Database
	 * @param key
	 * @return
	 */
	@GET
	@Path("/checkKeyAvailability")
	@UnitOfWork
	public Boolean findByKey(@QueryParam("customKey") String key) {
		return isKeyAvailable(key);
	}

	private Boolean isKeyAvailable(String key) {
		key = key.trim();
		if(key.isEmpty())return false;
		return teenyUrlDAO.findByKey(key).size() == 0;
	}

}
