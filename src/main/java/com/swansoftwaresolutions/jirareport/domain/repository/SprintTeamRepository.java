package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintTeam;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

public interface SprintTeamRepository {

    List<SprintTeam> findAll();

    List<SprintTeam> findByReportId(Long reportId);

    List<SprintTeam> findByReportIdAndAgileSprintId(Long reportId, Long agileSprintId);

    SprintTeam findById(Long id);

    SprintTeam findByAgileSprintId(Long agileSprintId);

    SprintTeam add(SprintTeam sprintTeam) throws NoSuchEntityException;

    SprintTeam update(SprintTeam sprintTeam) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(final SprintTeam sprintTeam) throws NoSuchEntityException;

    void delete(final Long sprintTeamId) throws NoSuchEntityException;

    void deleteByReportId(final Long reportId) throws NoSuchEntityException;
}
