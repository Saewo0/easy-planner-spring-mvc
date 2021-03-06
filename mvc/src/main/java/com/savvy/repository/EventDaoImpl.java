package com.savvy.repository;

import com.savvy.domain.Event;
import com.savvy.domain.Photo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EventDaoImpl extends CRUDDaoImpl<Event, Long> implements EventDao {
    @Autowired
    SessionFactory sessionFactory;

    public EventDaoImpl() {
        super(Event.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findByUserId(Long userId) {
        String hql_findByUserId = "FROM Event event where event.host.id = :userId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Event> query = session.createQuery(hql_findByUserId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
