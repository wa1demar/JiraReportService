package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface AdminReportMapper {

    CommentDto toDto(Comment comment);
    List<CommentDto> toDtos(List<Comment> commentList);
    Comment fromDto(CommentDto commentDto);
    List<Comment> fromDtos(List<CommentDto> commentDtoList);

}
