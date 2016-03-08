package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.mapper.CommentMapper;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Component
public class AdminReportMapperImpl implements CommentMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto toDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> toDtos(List<Comment> commentList) {
        Type targetistType = new TypeToken<List<CommentDto>>(){}.getType();
        return modelMapper.map(commentList, targetistType);
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    @Override
    public List<Comment> fromDtos(List<CommentDto> commentDtoList) {
        Type targetistType = new TypeToken<List<Comment>>(){}.getType();
        return modelMapper.map(commentDtoList, targetistType);
    }

}
