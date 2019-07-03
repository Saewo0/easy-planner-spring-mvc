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
public class UserDaoImpl extends CRUDDaoImpl<User, Long> implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    @Transactional
    public User findByIdAlongWithEvents(Long id) {
        String hql_findByIdEager = "FROM User u LEFT JOIN FETCH u.events where u.id = :userId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findByIdEager);
        query.setParameter("userId", id);
        return query.getSingleResult();
    }

    @Transactional
    public User findByUsername(String username) {
        String hql_findByUsername = "FROM User u where u.username = :username";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findByUsername);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Transactional
    public User findByEmailIgnoreCase(String email) {
        String hql_findByEmail = "FROM User u where lower(u.email) = :email";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findByEmail);
        query.setParameter("email", email.toLowerCase());
        return query.getSingleResult();
    }

    @Transactional
    public List<User> findByFirstNameIgnoreCase(String firstName) {
        String hql_findByFirstNameIgnoreCase = "FROM User u where lower(u.firstName) = :firstName";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findByFirstNameIgnoreCase);
        query.setParameter("firstName", firstName.toLowerCase());
        return query.getResultList();
    }

    @Transactional
    public List<User> findByLastNameIgnoreCase(String lastName) {
        String hql_findByLastNameIgnoreCase = "FROM User u where lower(u.lastName) = :lastName";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(hql_findByLastNameIgnoreCase);
        query.setParameter("lastName", lastName.toLowerCase());
        return query.getResultList();
    }
}
