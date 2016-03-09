package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

public interface SprintRepository {

    List<Sprint> findAll();

    List<Sprint> findByReportId(final Long reportId);

    Sprint findByReportIdAndAgileSprintId(final Long reportId, final Long agileSprintId);

    Sprint findById(final Long id);

    Sprint findLast();

    Sprint findByAgileSprintId(final Long agileSprintId);

    Sprint add(final Sprint sprint);

    Sprint update(final Sprint sprint)  throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(Sprint sprint) throws NoSuchEntityException;

    void delete(Long id) throws NoSuchEntityException;

    void deleteByReportId(Long reportId) throws NoSuchEntityException;
}
