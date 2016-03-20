package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.CommentDto;
import com.swansoftwaresolutions.jirareport.core.service.CommentService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
    private ResponseEntity<CommentDto> addNewComment(@PathVariable(value = "reportId") Long reportId, @RequestBody CommentDto commentDto) throws NoSuchEntityException {
        commentDto.setCreatedDate(new Date());
        commentDto.setReportId(reportId);
        commentDto = commentService.save(commentDto);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/comments/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<CommentDto> deleteCommentById(@PathVariable(value = "id") Long id) {
        try {
            commentService.delete(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new CommentDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "v1/reports/{reportId}/comments", method = RequestMethod.GET)
    private ResponseEntity<List<CommentDto>> findAllComments(@PathVariable("reportId") Long reportId) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        try {
            commentDtoList = commentService.findByReportId(reportId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

}
