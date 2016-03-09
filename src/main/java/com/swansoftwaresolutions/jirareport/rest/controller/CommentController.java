package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.CommentService;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;
import com.swansoftwaresolutions.jirareport.rest.dto.responce.ResponceCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @RequestMapping(value = "/v1/comment/", method = RequestMethod.GET)
    private ResponseEntity<ResponceCommentDto> makeReportCopyById(@RequestParam(value = "id") long id) {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;

        CommentDto commentDto = null;

        try {
            commentDto = commentService.findById(id);
            if (commentDto != null) {
                responseCommentDto = new ResponceCommentDto(true, "Comment found successfully.", commentDto);
                httpStatus = HttpStatus.OK;
            } else {
                responseCommentDto = new ResponceCommentDto(false, "Can't found Comment.", commentDto);
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            responseCommentDto = new ResponceCommentDto(false, "Can't found Comment.", commentDto);
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }


    @RequestMapping(value = "/v1/comment/", method = RequestMethod.POST)
    private ResponseEntity<ResponceCommentDto> addNewComment(@Valid @RequestBody CommentDto commentDto) throws NoSuchEntityException {
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

    @RequestMapping(value = "/v1/comment", method = RequestMethod.PATCH)
    public ResponseEntity<ResponceCommentDto> updateComment(@Valid @RequestBody CommentDto commentDto) throws NoSuchEntityException {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;

        CommentDto comment = commentService.update(commentDto);
        if (comment != null) {
            responseCommentDto = new ResponceCommentDto(true, "Comment updated successfully.", commentDto);
            httpStatus = HttpStatus.OK;
        } else {
            responseCommentDto = new ResponceCommentDto(false, "Can't update Comment.");
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
        }
        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }

    @RequestMapping(value = "/v1/comment", method = RequestMethod.DELETE)
    private ResponseEntity<ResponceCommentDto> deleteReportById(@RequestParam(value = "id") long id) {
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


    @RequestMapping(value = "/v1/comments/", method = RequestMethod.GET)
    private ResponseEntity<ResponceCommentDto> findAllComments() {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;

        List<CommentDto> commentDtoList = commentService.findAll();
        if (commentDtoList != null) {
            responseCommentDto = new ResponceCommentDto(true, "Comment found successfully.", commentDtoList);
            httpStatus = HttpStatus.OK;
        } else {
            responseCommentDto = new ResponceCommentDto(false, "Can't found Comments.", commentDtoList);
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }

    @RequestMapping(value = "/v1/comments/{reportId}", method = RequestMethod.GET)
    private ResponseEntity<ResponceCommentDto> findAllCommentsByReportId(@PathVariable(value = "reportId") Long reportId) {
        ResponceCommentDto responseCommentDto;
        HttpStatus httpStatus;

        List<CommentDto> commentDtoList = null;
        try {
            commentDtoList = commentService.findByReportId(reportId);
            if (commentDtoList != null) {
                responseCommentDto = new ResponceCommentDto(true, "Comment found successfully.", commentDtoList);
                httpStatus = HttpStatus.OK;
            } else {
                responseCommentDto = new ResponceCommentDto(false, "Can't found Comments.",commentDtoList);
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            responseCommentDto = new ResponceCommentDto(false, "Can't found Comments.",commentDtoList);
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseCommentDto, httpStatus);
    }


}
