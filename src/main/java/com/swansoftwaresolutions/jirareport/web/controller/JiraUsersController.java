package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class JiraUsersController {

    @Autowired
    JiraUserService jiraUserService;

    @RequestMapping(value = "/v1/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JiraUsersDto> getAllUsers() {
        return new ResponseEntity<>(jiraUserService.retrieveAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/users/filtered", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JiraUsersDto> getFilteredUsers() {
        return new ResponseEntity<>(jiraUserService.retrieveFilteredUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> addUserToBoard(@Valid @RequestBody NewResourceUserDto resourceUserDto) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.addUserToBoard(resourceUserDto);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> removeUserFromBoard(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.removeUserFromBoard(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> getuserInfo(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.findInfoByLogin(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login}/full_delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> removeUserFromBoardFully(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.removeUserFromBoardFully(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login}/attachment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> addAttachmentToUser(@PathVariable("login") String login, @RequestParam("file") MultipartFile file) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.addAttachment(login, file);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }
}
