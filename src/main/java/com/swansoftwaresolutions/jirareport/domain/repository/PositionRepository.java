package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Position;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface PositionRepository {
    List<Position> findAll();

    Position add(Position position);

    Position delete(Long id);

    Position update(Position position);

    Position findById(Long level);
}
