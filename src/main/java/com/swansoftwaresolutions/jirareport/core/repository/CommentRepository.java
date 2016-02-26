package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> getAllComments();

    List<Comment> getCommentsByReportId(final Long reportId);

    Comment getCommentById(final Long id);

    Comment createComment(final Comment comment);

    void updateComment(final Comment comment);

    void deleteAllComments();

    void deleteComment(final Comment comment);

    void deleteComment(final Long commentId);

    void deleteCommentsByReportId(final Long reportId);
}
