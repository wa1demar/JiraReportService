package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
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
public class JiraProjectRepositoryImplTest extends AbstractDbTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testAddNewProject() throws Exception {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setJiraId(12204L);
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        JiraProject newJiraProject = projectRepository.add(jiraProject);

        assertNotNull(newJiraProject.getId());
        assertEquals("BAM", newJiraProject.getKey());
        assertEquals("BAMDashboard", newJiraProject.getName());

    }

    @Test
    public void testUpdateProject() throws Exception {
        JiraProject jiraProject = projectRepository.findById(1L);
        jiraProject.setJiraId(12204L);
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        JiraProject updatedJiraProject = projectRepository.update(jiraProject);
        assertNotNull(updatedJiraProject.getId());
        assertEquals("BAM", updatedJiraProject.getKey());
        assertEquals("BAMDashboard", updatedJiraProject.getName());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongProject() throws Exception {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setJiraId(12204L);
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        JiraProject updatedJiraProject = projectRepository.update(jiraProject);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<JiraProject> jiraProjects = projectRepository.findAll();
        assertNotNull(jiraProjects);
        assertEquals(5, jiraProjects.size());
    }

    @Test
    public void testFindProjectById() throws Exception {
        JiraProject jiraProject = projectRepository.findById(1L);

        assertNotNull(jiraProject);
        assertEquals("CAS", jiraProject.getKey());
        assertEquals("CashHoppers", jiraProject.getName());
        assertEquals(new Long(10904), jiraProject.getJiraId());

    }

    @Test
    public void testFindProjectByWrongId() throws Exception {
        JiraProject jiraProject = projectRepository.findById(10L);
        assertNull(jiraProject);

    }

    @Test
    public void testDeleteProjectById() throws Exception {
        JiraProject jiraProject = projectRepository.findById(1L);
        assertNotNull(jiraProject);

        projectRepository.delete(jiraProject.getId());
        assertNull(projectRepository.findById(1L));
        assertEquals(4, projectRepository.findAll().size());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteProjectByWrongId() throws Exception {
        projectRepository.delete(10L);

    }

    @Test
    public void testDeleteProject() throws Exception {
        JiraProject jiraProject = projectRepository.findById(1L);
        assertNotNull(jiraProject);

        projectRepository.delete(jiraProject);
        assertNull(projectRepository.findById(1L));
        assertEquals(4, projectRepository.findAll().size());
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteWrongProject() throws Exception {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setJiraId(12204L);
        jiraProject.setKey("BAM");
        jiraProject.setName("BAMDashboard");

        projectRepository.delete(jiraProject);

    }
}
