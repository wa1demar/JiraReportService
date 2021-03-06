package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnPriority;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordRepository {
    ResourceColumn add(ResourceColumn resourceColumn);

    ResourceColumn update(ResourceColumn resourceColumn);

    ResourceColumn findDefaultColumn();

    List<ResourceColumn> findAll(boolean sorted);

    List<JiraUser> findUsersByColumnId(Long id);

    void removeColumn(Long id);

    void updatePriorities(ResourceColumnPriority[] columnPriorities);

    ResourceColumn findById(Long toAssignmentTypeId);

    void sort(ResourceColumnPriority[] columnPriorities);
}
