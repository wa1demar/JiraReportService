package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.domain.entity.AdminReport;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.dto.AdminReportDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface AdminReportMapper {

    AdminReportDto toDto(AdminReport adminReport);
    List<AdminReportDto> toDtos(List<AdminReport> adminReportList);
    AdminReport fromDto(AdminReportDto adminReportDto);
    List<AdminReport> fromDtos(List<AdminReportDto> adminReportDtoList);

    AdminReportDto toDtofromJiraUser(JiraUser jiraUser);
    List<AdminReportDto> toDtosfromJiraUsers(List<JiraUser> jiraUserList);
    JiraUser fromJiraUserDto(AdminReportDto adminReportDto);
    List<JiraUser> fromJiraUserDtos(List<AdminReportDto> adminReportDtoList);

}
