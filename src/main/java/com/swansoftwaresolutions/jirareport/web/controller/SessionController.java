package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.user.SessionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest/v1")
public class SessionController {

    @RequestMapping(value = "/check_session", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SessionDto> getSprint() {
        SessionDto sessionDto = new SessionDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        sessionDto.setUsername(auth.getName());
        sessionDto.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());

        return new ResponseEntity<SessionDto>(sessionDto, HttpStatus.OK);
    }
}
