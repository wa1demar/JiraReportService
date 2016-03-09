package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;

import java.util.List;

public interface SprintRepository {

    List<Sprint> findAll();

    List<Sprint> findSprintsByReportId(final Long reportId);

    Sprint getSprintByReportIdAndAgileSprintId(final Long reportId, final Long agileSprintId);

    Sprint getSprintById(final Long id);

    Sprint getLastAddedSprint();

    Sprint getSprintByAgileSprintId(final Long agileSprintId);

    Sprint add(final Sprint sprint);

    void update(final Sprint sprint);

    void deleteAllSprint();

    void delete(final Sprint sprint);

    void delete(final Long sprintId);

    void deleteSprintsByReportId(final Long reportId);
}
