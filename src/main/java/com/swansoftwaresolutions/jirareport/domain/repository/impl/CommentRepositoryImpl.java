package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Comment;
import com.swansoftwaresolutions.jirareport.domain.repository.CommentRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public void deleteAll() throws NoSuchEntityException {
        List<Comment> commentList = findAll();
        for (Comment comment : commentList){
            delete(comment);
        }
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
    public void delete(Long id) throws NoSuchEntityException{
        Comment comment = findById(id);
        if (comment != null) {
            sessionFactory.getCurrentSession().delete(comment);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void deleteByReportId(Long reportId) throws NoSuchEntityException {
        Comment comment =(Comment) sessionFactory.openSession()
                .createCriteria(Comment.class).add(Restrictions.eq("reportId", reportId)).uniqueResult();

        if (comment != null) {
            sessionFactory.getCurrentSession().delete(comment);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

}
