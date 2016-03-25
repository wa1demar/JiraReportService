package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.JiraPointDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface PointService {

    JiraPointDto add(JiraPointDto newReportDto) throws NoSuchEntityException;

    JiraPointDto update(JiraPointDto report) throws NoSuchEntityException;

    void delete(Long id) throws NoSuchEntityException;

    void delete(JiraPointDto reportDto) throws NoSuchEntityException;

    List<JiraPointDto> findAll() throws NoSuchEntityException;

    JiraPointDto findById(Long pointId) throws NoSuchEntityException;

    List<JiraPointDto> findByBoardId(Long reportId) throws NoSuchEntityException;

    List<JiraPointDto> findBySprintId(Long reportId) throws NoSuchEntityException;

}
