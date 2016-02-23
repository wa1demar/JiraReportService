package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.User;

/**
 * @author Vladimir Martynyuk
 */
public interface UserRepository {
    User findByUsername(String username);
}
