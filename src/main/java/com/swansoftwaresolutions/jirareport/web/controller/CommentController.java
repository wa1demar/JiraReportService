package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.CommentDto;
import com.swansoftwaresolutions.jirareport.core.service.CommentService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.responce.ResponceCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@RestController
@RequestMapping("/rest")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @RequestMapping(value = "/v1/reports/{reportId}/comments", method = RequestMethod.POST)
    private ResponseEntity<ResponceCommentDto> addNewComment(@PathVariable(value = "reportId") Long reportId, @RequestBody CommentDto commentDto) throws NoSuchEntityException {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;
        commentDto = commentService.save(commentDto);

        if (commentDto != null) {
            responseCommentDto = new ResponceCommentDto(true, "Comment added successfully.", commentDto);
            httpStatus = HttpStatus.OK;
        } else {
            responseCommentDto = new ResponceCommentDto(false, "Can't add Comment.");
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
        }
        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }

    @RequestMapping(value = "/v1/comments/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<ResponceCommentDto> deleteCommentById(@PathVariable(value = "id") Long id) {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;

        try {
            if (commentService.findById(id) != null) {
                commentService.delete(id);
                responseCommentDto = new ResponceCommentDto(true, "Comment deleted successfully.");
                httpStatus = HttpStatus.OK;
            } else {
                responseCommentDto = new ResponceCommentDto(false, "Can't found Comment.");
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            responseCommentDto = new ResponceCommentDto(false, "Can't found Comment.");
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }

    @RequestMapping(value = "v1/reports/{reportId}/comments", method = RequestMethod.GET)
    private ResponseEntity<ResponceCommentDto> findAllComments(@PathVariable("reportId") Long reportId) {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;

        List<CommentDto> commentDtoList = new ArrayList<>();
        try {
            commentDtoList = commentService.findByReportId(reportId);
            responseCommentDto = new ResponceCommentDto(true, "Comment found successfully.", commentDtoList);
            httpStatus = HttpStatus.OK;
        } catch (NoSuchEntityException e) {
            responseCommentDto = new ResponceCommentDto(false, "Can't found Comments.", commentDtoList);
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }

}
