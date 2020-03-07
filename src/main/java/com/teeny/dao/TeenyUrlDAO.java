package com.teeny.dao;

import com.teeny.model.TeenyUrl;
import com.teeny.model.Url;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TeenyUrlDAO extends AbstractDAO<TeenyUrl> {

	public static Session session;
	/**
	 * Constructor.
	 *
	 * @param sessionFactory Hibernate session factory.
	 */
	public TeenyUrlDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
//		session = sessionFactory.getCurrentSession();
	}
	
	/**
	 * Method returns all urls stored in the database.
	 *
	 * @return list of all urls stored in the database
	 */
	public List<TeenyUrl> findAll() {
		return list((Query<TeenyUrl>) namedQuery("com.teeny.model.TeenyUrl.findAll"));
	}
	
	/**
	 * Method looks for an TeenyUrl by id.
	 *
	 * @param id the id of an TeenyUrl we are looking for.
	 * @return Optional containing the found TeenyUrl or an empty Optional
	 * otherwise.
	 */
	public Optional<TeenyUrl> findById(long id) {
		return Optional.of(get(id));
	}
	
	public Optional<TeenyUrl> insertUrl(Url url) {

		TeenyUrl obj = new TeenyUrl(url.url);
		long id = persist(obj).getId();
		obj.setId(id);
		return Optional.of(obj);
	}
	
}
