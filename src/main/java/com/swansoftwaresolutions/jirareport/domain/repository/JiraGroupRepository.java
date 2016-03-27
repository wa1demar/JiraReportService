package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraGroupRepository {

    void save(List<JiraGroup> groups);

    JiraGroup findByName(String name);

    List<JiraGroup> findAll();
}
