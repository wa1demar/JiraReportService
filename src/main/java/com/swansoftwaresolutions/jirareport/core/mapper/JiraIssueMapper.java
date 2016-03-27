package com.swansoftwaresolutions.jirareport.core.mapper;


import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;

import java.util.List;

/**
 * @author Vitaliy Hollovko
 */
public interface JiraIssueMapper {

    JiraIssueDto toDto(JiraIssue jiraIssue);
    List<JiraIssueDto> toDtos(List<JiraIssue> jiraIssueList);
    JiraIssue fromDto(JiraIssueDto jiraIssueDto);
    List<JiraIssue> fromDtos(List<JiraIssueDto> jiraIssueDtoList);
}
