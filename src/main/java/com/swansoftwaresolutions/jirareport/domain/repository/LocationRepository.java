package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Location;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface LocationRepository {
    List<Location> findAll();

    Location add(Location location);

    Location delete(Long id);

    Location findById(Long id);

    Location update(Location location);
}
