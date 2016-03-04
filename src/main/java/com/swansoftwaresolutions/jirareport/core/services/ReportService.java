package com.swansoftwaresolutions.jirareport.core.services;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface ReportService {

    Report save(Report report);

    Report update(Report report) throws NoSuchEntityException;

}
