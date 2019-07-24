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
public class PhotoDaoImpl extends CRUDDaoImpl<Photo, Long> implements PhotoDao {
    @Autowired
    private SessionFactory sessionFactory;

    public PhotoDaoImpl() {
        super(Photo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Photo> findByUserId(Long userId) {
        String hql_findByUserId = "FROM Photo photo where photo.owner.id = :userId";
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Photo> query = session.createQuery(hql_findByUserId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
