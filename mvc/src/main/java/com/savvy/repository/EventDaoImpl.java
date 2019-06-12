package com.savvy.repository;

import com.savvy.domain.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Event save(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.save(event);
        return event;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findAll() {
        String hql_findAll = "FROM Event";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Event> query = session.createQuery(hql_findAll);
        return query.getResultList();
    }

    @Override
    public Event findByIdEager(Long id) {
//        String hql_findByIdEager = "FROM User u where u.id = :userId";
//        Session session = sessionFactory.getCurrentSession();
//        TypedQuery<User> query = session.createQuery(hql_findByIdEager);
//        query.setParameter("userId", id);
//        return query.getSingleResult();
        return null;
    }

    @Override
    public Event findById(Long id) {
        String hql_findByIdEager = "FROM Event e where e.id = :eventId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Event> query = session.createQuery(hql_findByIdEager);
        query.setParameter("eventId", id);
        return query.getSingleResult();
    }
}
