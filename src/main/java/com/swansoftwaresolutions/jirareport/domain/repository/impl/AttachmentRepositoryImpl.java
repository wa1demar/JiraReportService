package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Attachment;
import com.swansoftwaresolutions.jirareport.domain.repository.AttachmentRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Vladimir Martynyuk
 */
@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Attachment save(Attachment attachment) {
        sessionFactory.getCurrentSession().save(attachment);
        return attachment;
    }

    @Override
    public Attachment findById(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Attachment a WHERE a.id = :id");
        query.setParameter("id", id);
        return (Attachment) query.uniqueResult();
    }

    @Override
    public void delete(Attachment attachment) {
        sessionFactory.getCurrentSession().delete(attachment);
    }
}
