package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordRepository {
    ResourceColumn add(ResourceColumn resourceColumn);

    ResourceColumn update(ResourceColumn resourceColumn);
}
