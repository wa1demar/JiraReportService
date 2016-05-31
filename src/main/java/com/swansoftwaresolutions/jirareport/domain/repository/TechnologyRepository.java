package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Technology;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface TechnologyRepository {
    List<Technology> findAll();

    Technology add(Technology technology);

    Technology delete(Long id);

    Technology findById(Long id);

    Technology update(Technology technology);

    List<Technology> findAllByIds(Long[] technologies);

    List<Technology> findAllByIds(List<Long> ids);

    List<Technology> findBench();

}
