package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminReportDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface AdminReportService {

    List<AdminReportDto> findAll();

    List<AdminReportDto> findByReportId(final Long reportId);

    AdminReportDto findById(Long id);

    AdminReportDto save(AdminReportDto adminReportDto);

    AdminReportDto update(AdminReportDto adminReportDto) throws NoSuchEntityException;

    void delete(AdminReportDto adminReport) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(final Long id) throws NoSuchEntityException;

    void deleteByReportId(final Long reportId) throws NoSuchEntityException;
}
