package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.PointDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Point;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface PointService {

    PointDto add(PointDto newReportDto) throws NoSuchEntityException;

    PointDto update(PointDto report) throws NoSuchEntityException;

    void delete(Long id) throws NoSuchEntityException;

    void delete(PointDto reportDto) throws NoSuchEntityException;

    List<PointDto> findAll() throws NoSuchEntityException;

    PointDto findById(Long pointId) throws NoSuchEntityException;

}
