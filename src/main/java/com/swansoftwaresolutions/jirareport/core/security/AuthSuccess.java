package com.swansoftwaresolutions.jirareport.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swansoftwaresolutions.jirareport.core.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.rest.dto.UserDto;
import com.swansoftwaresolutions.jirareport.rest.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class AuthSuccess extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        UserLoginDto userDto = userService.retrieveLoggerUser(authentication.getName());
        userDto.setToken("dfghjrkjteltgjhfrlktghdfskjgh");
        ObjectMapper mapper = new ObjectMapper();
        String user = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDto);
        response.getWriter().write(user);

        response.setStatus(HttpServletResponse.SC_OK);
    }

}