package com.savvy.repository;

import com.savvy.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        String hql_findAll = "FROM User";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findAll);
        return query.getResultList();
    }

    @Override
    @Transactional
    public User findByIdEager(Long id) {
        String hql_findByIdEager = "FROM User u LEFT JOIN FETCH u.events where u.id = :userId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findByIdEager);
        query.setParameter("userId", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public User findById(Long id) {
        String hql_findById = "FROM User u where u.id = :userId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findById);
        query.setParameter("userId", id);
        return query.getSingleResult();
    }
}
