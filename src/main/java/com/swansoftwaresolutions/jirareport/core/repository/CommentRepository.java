package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

public interface CommentRepository {

    List<Comment> findAll();

    List<Comment> findByReportId(final Long reportId);

    Comment findById(final Long id);

    Comment add(final Comment comment);

    Comment update(final Comment comment) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(final Comment comment) throws NoSuchEntityException;

    void delete(final Long id) throws NoSuchEntityException;

    void deleteByReportId(final Long reportId) throws NoSuchEntityException;
}
