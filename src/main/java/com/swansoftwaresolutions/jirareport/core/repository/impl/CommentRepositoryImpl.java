package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.repository.CommentRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> findAll() {
        return sessionFactory.openSession().createCriteria(Comment.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> findByReportId(Long reportId) {
        return (List<Comment>) sessionFactory.openSession()
                .createCriteria(Comment.class)
                .add(Restrictions.eq("reportId", reportId))
                .list();
    }

    @Override
    public Comment findById(Long id) {
        return (Comment) sessionFactory.openSession()
                .createCriteria(Comment.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Comment add(Comment comment) {
        sessionFactory.getCurrentSession().persist(comment);
        return comment;
    }

    @Override
    public Comment update(Comment comment) throws NoSuchEntityException {
        if (findById(comment.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (Comment) sessionFactory.openSession().merge(comment);
    }

    @Override
    public void deleteAll() {
        Query query = sessionFactory.openSession().getNamedQuery("Comment.deleteAll");
        query.executeUpdate();
    }

    @Override
    public void delete(Comment comment) throws NoSuchEntityException {
        if (findById(comment.getId()) != null) {
            sessionFactory.getCurrentSession().delete(comment);
        } else {
            throw new NoSuchEntityException("Entity not found");
        }
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = findById(commentId);
        sessionFactory.getCurrentSession().delete(comment);

    }

    @Override
    public void deleteByReportId(Long reportId) throws NoSuchEntityException {

        if (findByReportId(reportId).size() == 0) {
            throw new NoSuchEntityException("Entities not found");
        }

        Query query = sessionFactory.openSession().getNamedQuery("Comment.deleteByReportId");
        query.setParameter("reportId", reportId);

        query.executeUpdate();
    }

}
