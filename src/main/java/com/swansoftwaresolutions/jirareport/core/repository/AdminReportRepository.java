package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.AdminReport;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

public interface AdminReportRepository {

    List<AdminReport> findAll();

    List<AdminReport> findByReportId(final Long reportId);

    AdminReport findById(final Long id);

    AdminReport add(final AdminReport adminReport);

    AdminReport update(final AdminReport adminReport) throws NoSuchEntityException;

    void delete(final AdminReport adminReport) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(final Long id) throws NoSuchEntityException;

    void deleteByReportId(final Long reportId) throws NoSuchEntityException;
}
