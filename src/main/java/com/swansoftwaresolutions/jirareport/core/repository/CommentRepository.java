package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> findAll();

    List<Comment> findByReportId(final Long reportId);

    Comment findById(final Long id);

    Comment add(final Comment comment);

    Comment update(final Comment comment);

    void deleteAll();

    void delete(final Comment comment);

    void delete(final Long commentId);

    void deleteByReportId(final Long reportId);
}
