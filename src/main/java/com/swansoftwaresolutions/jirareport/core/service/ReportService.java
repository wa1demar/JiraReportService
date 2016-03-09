package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface ReportService {

    List<ReportDto> findAll();

    ReportResponceDto save(ReportDto reportDto) throws NoSuchEntityException;

    Report update(Report report) throws NoSuchEntityException;

    ReportDto findById(long id) throws NoSuchEntityException;

    void deleteById(long id) throws NoSuchEntityException;

    void delete(ReportDto reportDto) throws NoSuchEntityException;

}
