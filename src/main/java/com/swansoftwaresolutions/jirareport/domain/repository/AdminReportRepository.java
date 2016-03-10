package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Admin;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

public interface AdminReportRepository {

    List<Admin> findAll();

    List<Admin> findByReportId(final Long reportId);

    Admin findById(final Long id);

    Admin add(final Admin admin);

    Admin update(final Admin admin) throws NoSuchEntityException;

    void delete(final Admin admin) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(final Long id) throws NoSuchEntityException;

    void deleteByReportId(final Long reportId) throws NoSuchEntityException;
}
