package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface DueDateRepository {
    List<JiraIssue> retrieveAllDueDatas();
}
