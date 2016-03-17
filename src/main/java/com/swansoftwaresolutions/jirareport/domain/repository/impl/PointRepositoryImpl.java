package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Point;
import com.swansoftwaresolutions.jirareport.domain.repository.PointRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class PointRepositoryImpl implements PointRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Point> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Point.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public Point add(Point point) {
        sessionFactory.getCurrentSession().save(point);
        return point;
    }

    @Override
    public Point findById(Long id) throws NoSuchEntityException {
        return (Point) sessionFactory.openSession()
                .createCriteria(Point.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Point update(Point point) throws NoSuchEntityException {
        if (findById(point.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        sessionFactory.openSession().update(point);

        return point;
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
    List<Point> reports = findAll();
        for (Point report : reports){
            delete(report);
        }
    }

    @Override
    public Point delete(Point report)  throws NoSuchEntityException {
        Point deletePoint = findById(report.getId());

        if (deletePoint != null) {
            sessionFactory.getCurrentSession().delete(report);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

        return deletePoint;
    }

    @Override
    public Point delete(Long reportId) throws NoSuchEntityException {
        Point point = findById(reportId);
        if (point != null) {
            sessionFactory.getCurrentSession().delete(point);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

        return point;
    }

}
