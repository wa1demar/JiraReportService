package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Sprint;

import java.util.List;

public interface SprintRepository {

    List<Sprint> findAll();

    List<Sprint> getSprintsByIdReportId(final Long reportId);

    Sprint getSprintByIdReportAndIdAgileSprintId(final Long reportId, final Long agileSprintId);

    Sprint getSprintById(final Long id);

    Sprint getLastAddedSprint();

    Sprint getSprintByIdAgileSprintId(final Long agileSprintId);

    Sprint add(final Sprint sprint);

    void update(final Sprint sprint);

    void deleteAllSprint();

    void delete(final Sprint sprint);

    void delete(final Long sprintId);

    void deleteSprintsByIdReportId(final Long reportId);
}
