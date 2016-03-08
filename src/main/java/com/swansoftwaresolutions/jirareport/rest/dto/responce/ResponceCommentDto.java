package com.swansoftwaresolutions.jirareport.rest.dto.responce;

import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

public class ResponceCommentDto implements Serializable{

    private Boolean success;
    private String  message;
    private CommentDto commentDto;
    private List<CommentDto> commentDtoList;


    public ResponceCommentDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponceCommentDto(Boolean success, String message, CommentDto commentDto) {
        this.success = success;
        this.message = message;
        this.commentDto = commentDto;
    }

    public ResponceCommentDto(Boolean success, String message, List<CommentDto> commentDtoList) {
        this.success = success;
        this.message = message;
        this.commentDtoList = commentDtoList;
    }

    public ResponceCommentDto(Boolean success, String message, CommentDto commentDto, List<CommentDto> commentDtoList) {
        this.success = success;
        this.message = message;
        this.commentDto = commentDto;
        this.commentDtoList = commentDtoList;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentDto getCommentDto() {
        return commentDto;
    }

    public void setCommentDto(CommentDto commentDto) {
        this.commentDto = commentDto;
    }

    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
