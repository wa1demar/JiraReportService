package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.model.Paged;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface DueDateRepository {
    Paged retrieveAllDueDatas(List<String> agileDoneNames, int page);
}
