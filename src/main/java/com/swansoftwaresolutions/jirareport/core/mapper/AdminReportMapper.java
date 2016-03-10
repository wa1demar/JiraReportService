package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.domain.entity.Admin;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminReportDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface AdminReportMapper {

    AdminReportDto toDto(Admin admin);
    List<AdminReportDto> toDtos(List<Admin> adminList);
    Admin fromDto(AdminReportDto adminReportDto);
    List<Admin> fromDtos(List<AdminReportDto> adminReportDtoList);

    AdminReportDto toDtofromJiraUser(JiraUser jiraUser);
    List<AdminReportDto> toDtosfromJiraUsers(List<JiraUser> jiraUserList);
    JiraUser fromJiraUserDto(AdminReportDto adminReportDto);
    List<JiraUser> fromJiraUserDtos(List<AdminReportDto> adminReportDtoList);

}
