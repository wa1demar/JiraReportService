package com.swansoftwaresolutions.jirareport.core.security;

import com.swansoftwaresolutions.jirareport.core.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.rest.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Martynyuk
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDto user = userService.retrieveByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("no user found with " + username);
        }

        return new AccountUserDetails(user);
    }
}
