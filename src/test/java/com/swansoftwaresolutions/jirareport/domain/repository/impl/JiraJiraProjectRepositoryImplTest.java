package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.AbstractDbTest;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraProjectRepository;
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
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/project_before.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/project_after.sql")
})
public class JiraJiraProjectRepositoryImplTest extends AbstractDbTest {

    @Autowired
    JiraProjectRepository jiraProjectRepository;

    @Test
    public void testAddNewProject() throws Exception {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        JiraProject newJiraProject = jiraProjectRepository.add(jiraProject);

        assertNotNull(newJiraProject.getId());
        assertEquals("BAM", newJiraProject.getKey());
        assertEquals("BAMDashboard", newJiraProject.getName());

    }

    @Test
    public void testUpdateProject() throws Exception {
        JiraProject jiraProject = jiraProjectRepository.findById(1L);
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        JiraProject updatedJiraProject = jiraProjectRepository.update(jiraProject);
        assertNotNull(updatedJiraProject.getId());
        assertEquals("BAM", updatedJiraProject.getKey());
        assertEquals("BAMDashboard", updatedJiraProject.getName());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongProject() throws Exception {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        JiraProject updatedJiraProject = jiraProjectRepository.update(jiraProject);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<JiraProject> jiraProjects = jiraProjectRepository.findAll();
        assertNotNull(jiraProjects);
        assertEquals(5, jiraProjects.size());
    }

    @Test
    public void testFindProjectById() throws Exception {
        JiraProject jiraProject = jiraProjectRepository.findById(1L);

        assertNotNull(jiraProject);
        assertEquals("CAS", jiraProject.getKey());
        assertEquals("CashHoppers", jiraProject.getName());

    }

    @Test
    public void testFindProjectByWrongId() throws Exception {
        JiraProject jiraProject = jiraProjectRepository.findById(10L);
        assertNull(jiraProject);

    }

    @Test
    public void testDeleteProjectById() throws Exception {
        JiraProject jiraProject = jiraProjectRepository.findById(1L);
        assertNotNull(jiraProject);

        jiraProjectRepository.delete(jiraProject.getId());
        assertNull(jiraProjectRepository.findById(1L));
        assertEquals(4, jiraProjectRepository.findAll().size());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteProjectByWrongId() throws Exception {
        jiraProjectRepository.delete(10L);

    }

    @Test
    public void testDeleteProject() throws Exception {
        JiraProject jiraProject = jiraProjectRepository.findById(1L);
        assertNotNull(jiraProject);

        jiraProjectRepository.delete(jiraProject);
        assertNull(jiraProjectRepository.findById(1L));
        assertEquals(4, jiraProjectRepository.findAll().size());
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteWrongProject() throws Exception {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        jiraProjectRepository.delete(jiraProject);

    }
}
