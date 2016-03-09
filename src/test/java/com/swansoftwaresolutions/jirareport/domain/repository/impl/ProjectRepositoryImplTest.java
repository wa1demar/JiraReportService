package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Project;
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
public class ProjectRepositoryImplTest extends AbstractDbTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testAddNewProject() throws Exception {
        Project project = new Project();
        project.setJiraId(12204L);
        project.setKey("BAM");
        project.setName("BAMDashboard");

        Project newProject = projectRepository.add(project);

        assertNotNull(newProject.getId());
        assertEquals("BAM", newProject.getKey());
        assertEquals("BAMDashboard", newProject.getName());

    }

    @Test
    public void testUpdateProject() throws Exception {
        Project project = projectRepository.findById(1L);
        project.setJiraId(12204L);
        project.setKey("BAM");
        project.setName("BAMDashboard");

        Project updatedProject = projectRepository.update(project);
        assertNotNull(updatedProject.getId());
        assertEquals("BAM", updatedProject.getKey());
        assertEquals("BAMDashboard", updatedProject.getName());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongProject() throws Exception {
        Project project = new Project();
        project.setJiraId(12204L);
        project.setKey("BAM");
        project.setName("BAMDashboard");

        Project updatedProject = projectRepository.update(project);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<Project> projects = projectRepository.findAll();
        assertNotNull(projects);
        assertEquals(5, projects.size());
    }

    @Test
    public void testFindProjectById() throws Exception {
        Project project = projectRepository.findById(1L);

        assertNotNull(project);
        assertEquals("CAS", project.getKey());
        assertEquals("CashHoppers", project.getName());
        assertEquals(new Long(10904), project.getJiraId());

    }

    @Test
    public void testFindProjectByWrongId() throws Exception {
        Project project = projectRepository.findById(10L);
        assertNull(project);

    }

    @Test
    public void testDeleteProjectById() throws Exception {
        Project project = projectRepository.findById(1L);
        assertNotNull(project);

        projectRepository.delete(project.getId());
        assertNull(projectRepository.findById(1L));
        assertEquals(4, projectRepository.findAll().size());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteProjectByWrongId() throws Exception {
        projectRepository.delete(10L);

    }

    @Test
    public void testDeleteProject() throws Exception {
        Project project = projectRepository.findById(1L);
        assertNotNull(project);

        projectRepository.delete(project);
        assertNull(projectRepository.findById(1L));
        assertEquals(4, projectRepository.findAll().size());
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteWrongProject() throws Exception {
        Project project = new Project();
        project.setJiraId(12204L);
        project.setKey("BAM");
        project.setName("BAMDashboard");

        projectRepository.delete(project);

    }
}
