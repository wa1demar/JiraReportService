package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Point;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface PointRepository {

    List<Point> findAll();

    Point findById(Long id) throws NoSuchEntityException;

    Point add(Point point);

    Point update(Point point) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    Point delete(Point point) throws NoSuchEntityException;

    Point delete(Long pointId) throws NoSuchEntityException;
}
