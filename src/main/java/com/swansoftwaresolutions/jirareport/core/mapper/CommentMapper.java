package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.CommentDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Comment;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface CommentMapper {

    CommentDto toDto(Comment comment);
    List<CommentDto> toDtos(List<Comment> commentList);
    Comment fromDto(CommentDto commentDto);
    List<Comment> fromDtos(List<CommentDto> commentDtoList);

}
