package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.ProjectRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

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
}
