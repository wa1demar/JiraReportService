package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ReportRepository {

    List<Report> findAll();

    Report findById(final Long id) throws NoSuchEntityException;

    Report findByBoardId(Long boardId) throws NoSuchEntityException;

    Report add(Report report);

    Report update(Report report) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    Report delete(Report report) throws NoSuchEntityException;

    Report delete(Long reportId) throws NoSuchEntityException;

    List<Report> findAllClosed();

    Paged findAllClosedPaginated(int page);

    List<Report> findAllOpened();

    Paged findAllOpenedPaginated(int page);

    long closedSprintCount(Long reportId);

    boolean showUat(Long reportId);

    long sprintCount(Long reportId);
}
