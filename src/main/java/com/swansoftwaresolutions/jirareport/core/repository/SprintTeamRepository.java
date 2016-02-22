package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.SprintTeam;

import java.util.List;

public interface SprintTeamRepository {

    List<SprintTeam> getAllSprintTeams();

    List<SprintTeam> getSprintTeamsByIdReport(final Long reportId);

    List<SprintTeam> getSprintTeamByIdReportAndIdAgileSprint(final Long reportId, final Long agileSprintId);

    SprintTeam getSprintTeamById(final Long id);

    SprintTeam getSprintTeamByIdAgileSprint(final Long agileSprintId);

    void createSprintTeam(final SprintTeam sprintTeam);

    void updateSprintTeam(final SprintTeam sprintTeam);

    void deleteAllSprintTeam();

    void deleteSprintTeam(final SprintTeam sprintTeam);

    void deleteSprintTeam(final Long sprintTeamId);

    void deleteSprintTeamsByIdReport(final Long reportId);
}
