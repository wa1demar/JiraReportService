package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Sprint;

import java.util.List;

public interface SprintRepository {

    List<Sprint> getAllSprints();

    List<Sprint> getSprintsByIdReportId(final Long reportId);

    Sprint getSprintByIdReportAndIdAgileSprintId(final Long reportId, final Long agileSprintId);

    Sprint getSprintById(final Long id);

    Sprint getLastAddedSprint();

    Sprint getSprintByIdAgileSprintId(final Long agileSprintId);

    void createSprint(final Sprint sprint);

    void updateSprint(final Sprint sprint);

    void deleteAllSprint();

    void deleteSprint(final Sprint sprint);

    void deleteSprint(final Long sprintId);

    void deleteSprintsByIdReportId(final Long reportId);
}
