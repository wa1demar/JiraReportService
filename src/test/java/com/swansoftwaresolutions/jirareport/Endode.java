package com.swansoftwaresolutions.jirareport;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * @author Vladimir Martynyuk
 */
public class Endode {

    @Test
    public void getPassword() {
        String password = "password";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(password).toString());

    }
}
