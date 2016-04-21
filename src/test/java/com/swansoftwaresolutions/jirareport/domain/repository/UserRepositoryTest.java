package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vladimir Martynyuk
 */
public class UserRepositoryTest extends AbstractDbTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsernameTest() throws Exception {
        User user = userRepository.findByUsername("testUser");

        assertNotNull(user);
        assertEquals(new Long(1), user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("$2a$10$dnZM0.uNtg2g35gXubaRiOF/PaE65HavaEa5cGWVueIVPoX2Db8KC", user.getPassword());
        assertEquals("testuser@email.com", user.getEmail());
        assertEquals("ACTIVE", user.getStatus().toString());
    }
}
