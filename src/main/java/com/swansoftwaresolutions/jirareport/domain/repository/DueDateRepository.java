package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.model.Paged;

/**
 * @author Vladimir Martynyuk
 */
public interface DueDateRepository {
    Paged retrieveAllDueDatas(int page);
}
