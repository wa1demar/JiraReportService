package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Attachment;
import com.swansoftwaresolutions.jirareport.domain.repository.AttachmentRepository;
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
}
