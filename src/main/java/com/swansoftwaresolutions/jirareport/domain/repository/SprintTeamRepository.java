package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintTeam;

import java.util.List;

public interface SprintTeamRepository {

    List<SprintTeam> findAll();

    List<SprintTeam> getSprintTeamsByReportId(final Long reportId);

    List<SprintTeam> getSprintTeamByReportIdAndAgileSprintId(final Long reportId, final Long agileSprintId);

    SprintTeam getSprintTeamById(final Long id);

    SprintTeam getSprintTeamByAgileSprintId(final Long agileSprintId);

    void createSprintTeam(final SprintTeam sprintTeam);

    void updateSprintTeam(final SprintTeam sprintTeam);

    void deleteAllSprintTeam();

    void deleteSprintTeam(final SprintTeam sprintTeam);

    void deleteSprintTeam(final Long sprintTeamId);

    void deleteSprintTeamsByReportId(final Long reportId);
}
