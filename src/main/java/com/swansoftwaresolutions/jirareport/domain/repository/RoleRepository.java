package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Role;
import com.swansoftwaresolutions.jirareport.domain.enums.UserRole;

import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
public interface RoleRepository {
    Set<Role> findByName(UserRole roleManager);
}
