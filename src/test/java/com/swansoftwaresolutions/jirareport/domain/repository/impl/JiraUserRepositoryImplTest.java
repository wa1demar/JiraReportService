package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/user_before.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/user_after.sql")
})
public class JiraUserRepositoryImplTest extends AbstractDbTest {

    @Autowired
    JiraUserRepository userRepository;

    @Test
    public void testAddNewUser() throws Exception {
        JiraUser user = new JiraUser();
        user.setEmail("new.user@gmail.com");
        user.setLogin("newUser");
        user.setFullName("New User");
        user.setJiraUserId(10006L);

        JiraUser newUser = userRepository.add(user);

        assertNotNull(newUser.getId());
        assertEquals("new.user@gmail.com", newUser.getEmail());
        assertEquals("newUser", newUser.getLogin());
        assertEquals("New User", newUser.getFullName());
        assertEquals(new Long(10006), newUser.getJiraUserId());

    }

    @Test
    public void testUpdateUser() throws Exception {
        JiraUser user = userRepository.findById(1L);
        user.setEmail("new.user@gmail.com");
        user.setLogin("newUser");
        user.setFullName("New User");
        user.setJiraUserId(10006L);

        JiraUser updatedUser = userRepository.update(user);
        assertNotNull(updatedUser.getId());
        assertEquals("new.user@gmail.com", updatedUser.getEmail());
        assertEquals("newUser", updatedUser.getLogin());
        assertEquals("New User", updatedUser.getFullName());
        assertEquals(new Long(10006), updatedUser.getJiraUserId());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongUser() throws Exception {
        JiraUser user = new JiraUser();
        user.setEmail("new.user@gmail.com");
        user.setLogin("newUser");
        user.setFullName("New User");
        user.setJiraUserId(10006L);

        JiraUser updatedUser = userRepository.update(user);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<JiraUser> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(5, users.size());
    }

    @Test
    public void testFindUserById() throws Exception {
        JiraUser user = userRepository.findById(1L);

        assertNotNull(user);
        assertEquals("user1@gmail.com", user.getEmail());
        assertEquals("user1", user.getLogin());
        assertEquals("User One", user.getFullName());
        assertEquals(new Long(10000), user.getJiraUserId());

    }

    @Test
    public void testFindUserByWrongId() throws Exception {
        JiraUser user = userRepository.findById(10L);
        assertNull(user);

    }

    @Test
    public void testDeleteUserById() throws Exception {
        JiraUser user = userRepository.findById(1L);
        assertNotNull(user);

        userRepository.delete(user.getId());
        assertNull(userRepository.findById(1L));
        assertEquals(4, userRepository.findAll().size());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteUserByWrongId() throws Exception {
        userRepository.delete(10L);

    }

    @Test
    public void testDeleteUser() throws Exception {
        JiraUser user = userRepository.findById(1L);
        assertNotNull(user);

        userRepository.delete(user);
        assertNull(userRepository.findById(1L));
        assertEquals(4, userRepository.findAll().size());
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteWrongUser() throws Exception {
        JiraUser user = new JiraUser();
        user.setEmail("new.user@gmail.com");
        user.setLogin("newUser");
        user.setFullName("New User");
        user.setJiraUserId(10006L);

        userRepository.delete(user);

    }
}
