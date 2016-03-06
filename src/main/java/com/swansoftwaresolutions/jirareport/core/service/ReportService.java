package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface ReportService {

    NewReportDto save(NewReportDto newReportDto);

    Report update(Report report) throws NoSuchEntityException;

}
