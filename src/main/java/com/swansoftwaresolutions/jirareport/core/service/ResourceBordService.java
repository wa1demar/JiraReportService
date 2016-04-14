package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordService {
    ResourceColumnDto add(ResourceColumnDto columnDto);

    ResourceColumnDto update(ResourceColumnDto columnDto);
}
