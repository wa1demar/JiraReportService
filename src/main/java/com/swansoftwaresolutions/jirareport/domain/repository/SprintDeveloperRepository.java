package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;

import java.util.List;
import java.util.stream.StreamSupport;

public interface SprintDeveloperRepository {

    List<SprintDeveloper> findBySprintId(long sprintId);

    SprintDeveloper add(SprintDeveloper developer);

    void delete(List<Long> ids, Long sprintId);

    void deleteBySprintId(Long sprintId);

    List<SprintDeveloper> findByIds(List<Long> ids);
}
