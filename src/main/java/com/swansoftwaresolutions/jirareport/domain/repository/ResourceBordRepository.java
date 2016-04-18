package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordRepository {
    ResourceColumn add(ResourceColumn resourceColumn);

    ResourceColumn update(ResourceColumn resourceColumn);

    ResourceColumn findDefaultColumn();

    List<ResourceColumn> findAll();
}
