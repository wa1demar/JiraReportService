package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface CommentService {

    List<CommentDto> findAll();

    CommentDto save(CommentDto commentDto);

    CommentDto update(CommentDto commentDto) throws NoSuchEntityException;

    CommentDto findById(long id) throws NoSuchEntityException;

    List<CommentDto> findByReportId(long reportId) throws NoSuchEntityException;

    void delete(long id) throws NoSuchEntityException;

    void delete(CommentDto commentDto) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;
}
