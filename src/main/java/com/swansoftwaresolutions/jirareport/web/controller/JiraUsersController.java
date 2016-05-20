package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MoveMemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectIdDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDtoList;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyId;
import com.swansoftwaresolutions.jirareport.core.service.AttachmentService;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class JiraUsersController {

    @Autowired
    JiraUserService jiraUserService;

    @Autowired
    AttachmentService attachmentService;

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

    @RequestMapping(value = "/v1/members/{login:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> getUserInfo(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.findInfoByLogin(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> updateMemberInfo(@PathVariable("login") String login, @Valid @RequestBody MemberDto memberDto) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.updateMemberInfo(login, memberDto);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/move", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> moveMember(@PathVariable("login") String login, @Valid @RequestBody MoveMemberDto memberDto) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.moveMember(login, memberDto);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/move2", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<FullResourceColumnDtoList> move2Member(@PathVariable("login") String login, @Valid @RequestBody MoveMemberDto memberDto) throws NoSuchEntityException {

        FullResourceColumnDtoList newNewResourceUserDto = jiraUserService.moveMemberFull(login, memberDto);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/full_delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> removeUserFromBoardFully(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.removeUserFromBoardFully(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/attachment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> addAttachmentToUser(@PathVariable("login") String login, @RequestParam("file") MultipartFile file) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.addAttachment(login, file);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/attachment/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> deleteAttachmentFromUser(@PathVariable("id") Long id, @PathVariable("login") String login) throws NoSuchEntityException {

        jiraUserService.deleteAttachment(id);

        ResourceUserDto infoByLogin = jiraUserService.findInfoByLogin(login);

        return new ResponseEntity<>(infoByLogin, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/technologies", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> addTechnologiesToUser(@PathVariable("login") String login, @Valid @RequestBody TechnologyId technologyId) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.addTechnologies(login, technologyId);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/technologies/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> addTechnologiesToUser(@PathVariable("login") String login, @PathVariable("id") Long technologyId) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.deleteTechnology2(login, technologyId);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/attachment/{id}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("login") String login, @PathVariable("id") Long id) throws IOException {

        InputStream inputStream = attachmentService.loadFile(response, id);

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/v1/members/{login:.+}/projects", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> addProject(@PathVariable("login") String login, @Valid @RequestBody ProjectIdDto projectIdDto) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.addProject(login, projectIdDto);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}/projects/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> deleteProject(@PathVariable("login") String login, @PathVariable("id") Long id) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.deleteProject(login, id);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }


}
