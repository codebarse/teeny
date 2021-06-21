package com.teeny.dao;

import com.teeny.model.TeenyUrl;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TeenyUrlDAO extends AbstractDAO<TeenyUrl> {
	
	/**
	 * Constructor.
	 *
	 * @param sessionFactory Hibernate session factory.
	 */
	SessionFactory sessionFactory;
	public TeenyUrlDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sessionFactory = sessionFactory;
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
	 * Method looks for a TeenyUrl by url.
	 *
	 * @param url we are looking for
	 * @return Optional containing the found TeenyUrl or an empty Optional
	 * otherwise.
	 */
	public List<TeenyUrl> findByUrl(String url) {
		List<TeenyUrl> rows = (List<TeenyUrl>) namedQuery("com.teeny.model.TeenyUrl.findByUrl").setParameter("url", url).list();
		return rows;
	}

	/**
	 * Method looks for a TeenyUrl by key.
	 *
	 * @param key we are looking for
	 * @return Optional containing the found TeenyUrl or an empty Optional
	 * otherwise.
	 */
	public List<TeenyUrl> findByKey(String key) {
		List<TeenyUrl> rows = (List<TeenyUrl>) namedQuery("com.teeny.model.TeenyUrl.findByKey").setParameter("key", key).list();
		return rows;
	}

	/**
	 * Method looks for an TeenyUrl by id.
	 *
	 * @param id the id of an TeenyUrl we are looking for.
	 * @return Optional containing the found TeenyUrl or an empty Optional
	 * otherwise.
	 */
	public Optional<TeenyUrl> findById(long id) {
		return Optional.ofNullable(get(id));
	}
	
	public Optional<TeenyUrl> insertTeenyUrl(TeenyUrl teenyUrl) {
		return Optional.of(persist(teenyUrl));
	}
	
}
