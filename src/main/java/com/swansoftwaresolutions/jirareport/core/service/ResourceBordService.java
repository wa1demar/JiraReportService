package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordService {
    ResourceColumnDto add(ResourceColumnDto columnDto);

    ResourceColumnDto update(ResourceColumnDto columnDto);

    FullResourceColumnDtoList getColumns();

    void deleteColumn(Long id) throws NoSuchEntityException;

    List<ResourceColumnDto> getColumnsList(boolean b);

    List<ResourceColumnDto> updatePriority(ResourceColumnPriority[] columnPriorities);

    FullResourceColumnDtoList getColumns(ResourceFilterData filterData);

    FullResourceColumnDtoList sort(SortingColumnsObject columnPriorities);
}
