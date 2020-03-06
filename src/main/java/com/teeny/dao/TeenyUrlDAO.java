package com.teeny.dao;

import com.teeny.models.TeenyUrl;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TeenyUrlDAO extends AbstractDAO<TeenyUrl> {

    /**
     * Constructor.
     *
     * @param sessionFactory Hibernate session factory.
     */
    public TeenyUrlDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Method returns all urls stored in the database.
     *
     * @return list of all urls stored in the database
     */
    public List<TeenyUrl> findAll() {
        return list((Query<TeenyUrl>) namedQuery("com.teeny.models.TeenyUrl.findAll"));
    }

}
