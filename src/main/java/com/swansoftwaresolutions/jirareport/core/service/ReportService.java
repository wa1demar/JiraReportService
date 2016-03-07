package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.ReportDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface ReportService {

    List<ReportDto> findAll();

    NewReportDto save(NewReportDto newReportDto) throws NoSuchEntityException;

    Report update(Report report) throws NoSuchEntityException;

    ReportDto findById(long id) throws NoSuchEntityException;

    NewReportDto findNewReportById(long id) throws NoSuchEntityException;

    void deleteById(long id) throws NoSuchEntityException;

    void delete(ReportDto reportDto) throws NoSuchEntityException;

}
