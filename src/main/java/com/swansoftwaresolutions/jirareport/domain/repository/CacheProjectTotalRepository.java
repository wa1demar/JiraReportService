package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.CacheProjectTotal;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface CacheProjectTotalRepository {

    CacheProjectTotal add(CacheProjectTotal projectTotal);
    CacheProjectTotal update(CacheProjectTotal projectTotal);
    CacheProjectTotal saveOrUpdate(CacheProjectTotal projectTotal);
    void delete(Long id);
    CacheProjectTotal findById(Long id);
    List<CacheProjectTotal> findAll();
    CacheProjectTotal findByReportId(Long reportId);

    void deleteByReportId(long reportId);
}
