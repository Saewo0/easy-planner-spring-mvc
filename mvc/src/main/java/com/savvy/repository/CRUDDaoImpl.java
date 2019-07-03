package com.savvy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;


public class CRUDDaoImpl<T, ID> implements CRUDDao<T, ID> {

    private final Class<T> entityClass;

    @Autowired
    private SessionFactory sessionFactory;

    protected CRUDDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public T save(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
        return obj;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        String hql_findAll = "FROM " + this.entityClass.getSimpleName();
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<T> query = session.createQuery(hql_findAll);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) {
        String hql_findById = "FROM " + this.entityClass.getSimpleName() + " obj where obj.id = :objId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<T> query = session.createQuery(hql_findById);
        query.setParameter("objId", id);
        return query.getSingleResult();
    }
}
