package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.CacheProjectTotal;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;

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
    Paged findAllPaged(int page);
    CacheProjectTotal findByReportId(Long reportId);

    void deleteByReportId(long reportId);
}
