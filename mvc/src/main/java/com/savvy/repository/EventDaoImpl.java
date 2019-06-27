package com.savvy.repository;

import com.savvy.domain.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventDaoImpl extends CRUDDaoImpl<Event, Long> implements EventDao {

    public EventDaoImpl() {
        super(Event.class);
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
}
