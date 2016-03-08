package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.CommentService;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @RequestMapping(value = "/v1/comment", method = RequestMethod.POST)
    private ResponseEntity<CommentDto> addNewComment(@Valid @RequestBody CommentDto commentDto) throws NoSuchEntityException {
        commentDto = commentService.save(commentDto);

        if (commentDto != null) {
            return new ResponseEntity<>(commentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(commentDto, HttpStatus.NO_CONTENT);
        }

    }


}
