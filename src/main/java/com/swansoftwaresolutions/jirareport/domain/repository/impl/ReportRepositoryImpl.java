package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Query;
import org.hibernate.Session;
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
public class ReportRepositoryImpl implements ReportRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Report> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Report r");
        List<Report> reports = query.list();
        return reports;
    }

    @Override
    public Report findById(Long id) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Report r WHERE r.id = :id");
        query.setParameter("id", id);

        Report report = (Report) query.uniqueResult();
        if (report != null) {
            return report;
        }

        throw new NoSuchEntityException("Report Not Found");
    }

    @Override
    public Report findByBoardId(Long boardId) throws NoSuchEntityException{
        return (Report) sessionFactory.getCurrentSession()
                .createCriteria(Report.class).add(Restrictions.eq("boardId", boardId)).uniqueResult();

    }

    @Override
    public Report add(Report report) {
        sessionFactory.getCurrentSession().save(report);
        return report;
    }

    @Override
    public Report update(Report report) throws NoSuchEntityException {
        if (findById(report.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        sessionFactory.getCurrentSession().merge(report);

        return report;
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
    List<Report> reports = findAll();
        for (Report report : reports){
            delete(report);
        }
    }

    @Override
    public Report delete(Report report)  throws NoSuchEntityException {
        Report deleteReport = findById(report.getId());

        if (deleteReport != null) {
            sessionFactory.getCurrentSession().delete(report);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

        return deleteReport;
    }

    @Override
    public Report delete(Long reportId) throws NoSuchEntityException {
        Report report = findById(reportId);
        if (report != null) {
            sessionFactory.getCurrentSession().delete(report);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

        return report;
    }

    @Override
    public List<Report> findAllClosed() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Report r WHERE r.isClosed = true ORDER BY r.title ASC");
        return query.list();
    }

    @Override
    public Paged findAllClosedPaginated(int page) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Report r WHERE r.isClosed = true ORDER BY r.title ASC");
        Query count = sessionFactory.getCurrentSession().createQuery("select count(r.id) FROM Report r WHERE r.isClosed = true");
        Paged paged = new Paged();
        paged.setPage(page);
        paged.setTotal((int)((long)count.uniqueResult()));
        paged.setList(query.setFirstResult((page - 1) * 10).setMaxResults(10).list());
        return paged;
    }

    @Override
    public List<Report> findAllOpened() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Report r WHERE r.isClosed = false  ORDER BY r.title ASC");
        return query.list();
    }

    @Override
    public Paged findAllOpenedPaginated(int page) {
        Session session = sessionFactory.openSession();
        Paged paged = new Paged();

        try {
            Query query = session.createQuery("FROM Report r WHERE r.isClosed = false  ORDER BY r.title ASC ");
            Query count = session.createQuery("select count(r.id) FROM Report r WHERE r.isClosed = false ");
            paged.setPage(page);
            paged.setTotal((int) ((long) count.uniqueResult()));
            paged.setList(query.setFirstResult((page - 1) * 10).setMaxResults(10).list());
        } finally {
            session.close();
        }
        return paged;
    }

    @Override
    public long closedSprintCount(Long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(s) FROM Sprint s WHERE s.state = 'closed' AND s.report.id = :reportId");
        query.setParameter("reportId", reportId);
        return (long) query.uniqueResult();
    }

    @Override
    public boolean showUat(Long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(s) FROM Sprint s WHERE s.showUAT = false AND s.report.id = :reportId");
        query.setParameter("reportId", reportId);
        return (long) query.uniqueResult() == 0;
    }

    @Override
    public long sprintCount(Long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(s) FROM Sprint s WHERE s.report.id = :reportId");
        query.setParameter("reportId", reportId);
        return (long) query.uniqueResult();
    }

    @Override
    public List<Report> findAllAutomatic() {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT r FROM Report r WHERE r.boardId != null ");
        return query.list();
    }

}
