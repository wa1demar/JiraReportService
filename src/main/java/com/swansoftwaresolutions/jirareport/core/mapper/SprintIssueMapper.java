package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface SprintIssueMapper {

    SprintIssue fromDto(SprintIssueDto sprintIssueDto);

    SprintIssueDto toDto(SprintIssue sprintIssue);

    List<SprintIssue> fromDtos(List<SprintIssueDto> sprintIssueDtos);

    List<SprintIssueDto> toDtos(List<SprintIssue> sprintIssues);
}
