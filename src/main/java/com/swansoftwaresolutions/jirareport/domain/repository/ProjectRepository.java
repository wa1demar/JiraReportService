package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberPositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnPriority;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectRepository {
    Project save(Project project);

    List<Project> findAll();

    Project findById(Long projectId);

    void delete(Long id);

    Project update(Project project);

    void sortMembers(List<MemberPositionDto> users, Long projectId);

    void sort(List<ResourceColumnPriority> projectPositionDto);

}
