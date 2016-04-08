package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.DueDate;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
import com.swansoftwaresolutions.jirareport.domain.repository.DueDateRepository;
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
public class DueDateRepositoryImpl implements DueDateRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Paged retrieveAllDueDatas(List<String> agileDoneNames, int page) {
        String hql = "FROM JiraIssue i WHERE (i.dueDates.size > 1 or i.dueDate < current_date)";
        if (agileDoneNames != null && agileDoneNames.size() > 0) {
            hql += " and (i.statusName in (:agileDoneNames))";
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameterList("agileDoneNames", agileDoneNames);
        Query count = sessionFactory.getCurrentSession().createQuery("select count(*) " + hql);
        count.setParameterList("agileDoneNames", agileDoneNames);

        Paged paged = new Paged();
        paged.setPage(page);
        paged.setTotal((int)((long)count.uniqueResult()));
        paged.setList(query.setFirstResult((page - 1) * 10).setMaxResults(10).list());
        return paged;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DueDate> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM DueDate d order by d.updatedAt desc ");
        return  query.list();
    }

    @Override
    public void add(DueDate dueDate) {
        sessionFactory.getCurrentSession().save(dueDate);
    }
}
