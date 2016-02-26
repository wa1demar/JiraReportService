package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.repository.CommentRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Comment> getAllComments() {
        return sessionFactory.getCurrentSession().createCriteria(Comment.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Comment> getCommentsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Comment.findByReportId");
        query.setParameter("reportId", reportId);

        @SuppressWarnings("unchecked")
        List<Comment> comments = (List<Comment>) query.list();

        return comments;
    }

    @Override
    public Comment getCommentById(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Comment.findById");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        Comment comment = (Comment) query.uniqueResult();

        return comment;
    }

    @Override
    public Comment createComment(Comment comment) {
        return (Comment) sessionFactory.getCurrentSession().save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        sessionFactory.getCurrentSession().update(comment);
    }

    @Override
    public void deleteAllComments() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Comment.deleteAll");

        query.executeUpdate();
    }

    @Override
    public void deleteComment(Comment comment) {
        sessionFactory.getCurrentSession().delete(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        sessionFactory.getCurrentSession().delete(commentId);
    }

    @Override
    public void deleteCommentsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Comment.deleteByReportId");
        query.setParameter("reportId", reportId);

        query.executeUpdate();
    }
}
