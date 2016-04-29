package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologiesDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyId;
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
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;

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

    @RequestMapping(value = "/v1/members/{login:.+}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> removeUserFromBoard(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.removeUserFromBoard(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> getuserInfo(@PathVariable("login") String login) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.findInfoByLogin(login);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{login:.+}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResourceUserDto> updateMemberInfo(@PathVariable("login") String login, @Valid @RequestBody MemberDto memberDto) throws NoSuchEntityException {

        ResourceUserDto newNewResourceUserDto = jiraUserService.updateMemberInfo(login, memberDto);

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
    public ResponseEntity<ResourceUserDto> addAttachmentToUser(@PathVariable("id") Long id, @PathVariable("login") String login) throws NoSuchEntityException {

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

//    @RequestMapping(value="/download/{type}", method = RequestMethod.GET)
//    public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws IOException {

//        File file = null;
//
//        if(type.equalsIgnoreCase("internal")){
//            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//            file = new File(classloader.getResource(INTERNAL_FILE).getFile());
//        }else{
//            file = new File(EXTERNAL_FILE_PATH);
//        }
//
//        if(!file.exists()){
//            String errorMessage = "Sorry. The file you are looking for does not exist";
//            System.out.println(errorMessage);
//            OutputStream outputStream = response.getOutputStream();
//            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//            outputStream.close();
//            return;
//        }
//
//        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
//        if(mimeType==null){
//            System.out.println("mimetype is not detectable, will take default");
//            mimeType = "application/octet-stream";
//        }
//
//        System.out.println("mimetype : "+mimeType);
//
//        response.setContentType(mimeType);
//
//        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
//            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
//        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
//
//
//        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
//        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
//
//        response.setContentLength((int)file.length());

//        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
//        FileCopyUtils.copy(inputStream, response.getOutputStream());
//    }


}
