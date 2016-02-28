package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.repository.CommentRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository("commentRepository")
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
    public Comment createComment(Comment comment) {
        sessionFactory.getCurrentSession().persist(comment);
        return comment;
    }

    @Override
    public void updateComment(Comment comment) {
        sessionFactory.openSession().update(comment);
    }

    @Override
    public void deleteAllComments() {
        Query query = sessionFactory.openSession().getNamedQuery("Comment.deleteAll");
        query.executeUpdate();
    }

    @Override
    public void deleteComment(Comment comment) {
        sessionFactory.openSession().delete(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        sessionFactory.openSession().delete(commentId);
    }

    @Override
    public void deleteCommentsByReportId(Long reportId) {
        Query query = sessionFactory.openSession().getNamedQuery("Comment.deleteByReportId");
        query.setParameter("reportId", reportId);

        query.executeUpdate();
    }
}
