package com.savvy.repository;

import com.savvy.domain.Authority;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class AuthorityDaoImpl extends CRUDDaoImpl<Authority, Long> implements AuthorityDao {

    public AuthorityDaoImpl() {
        super(Authority.class);
    }

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Authority> findByUserId(Long userId) {
        String hql_findByUserId = "FROM Authority auth where auth.user.id = :userId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Authority> query = session.createQuery(hql_findByUserId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
