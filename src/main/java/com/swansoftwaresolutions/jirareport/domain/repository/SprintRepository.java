package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintRepository {
    Sprint add(Sprint sprint);

    Sprint update(Sprint sprint);

    List<Sprint> findByReportId(long reportId);

    Sprint delete(long sprintId) throws NoSuchEntityException;

    Sprint findById(long sprintId) throws NoSuchEntityException;

    void deleteByReportId(long id);

    void addAll(List<Sprint> sprints);
}
