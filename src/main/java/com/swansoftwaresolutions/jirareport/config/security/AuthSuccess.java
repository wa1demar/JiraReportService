package com.swansoftwaresolutions.jirareport.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserLoginDto;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        ObjectMapper mapper = new ObjectMapper();
        String user = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDto);
        response.getWriter().write(user);

        response.setStatus(HttpServletResponse.SC_OK);
    }

}