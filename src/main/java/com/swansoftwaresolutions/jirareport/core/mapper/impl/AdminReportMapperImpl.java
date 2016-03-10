package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.AdminReportMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Admin;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminReportDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Component
public class AdminReportMapperImpl implements AdminReportMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AdminReportDto toDto(Admin admin) {
        return modelMapper.map(admin, AdminReportDto.class);
    }

    @Override
    public List<AdminReportDto> toDtos(List<Admin> adminList) {
        Type targetistType = new TypeToken<List<AdminReportDto>>(){}.getType();
        return modelMapper.map(adminList, targetistType);
    }

    @Override
    public Admin fromDto(AdminReportDto adminReportDto) {
        return modelMapper.map(adminReportDto, Admin.class);
    }

    @Override
    public List<Admin> fromDtos(List<AdminReportDto> adminReportDtoList) {
        Type targetistType = new TypeToken<List<Admin>>(){}.getType();
        return modelMapper.map(adminReportDtoList, targetistType);
    }

    @Override
    public AdminReportDto toDtofromJiraUser(JiraUser jiraUser) {
        return modelMapper.map(jiraUser, AdminReportDto.class);
    }

    @Override
    public List<AdminReportDto> toDtosfromJiraUsers(List<JiraUser> jiraUserList) {
        Type targetistType = new TypeToken<List<AdminReportDto>>(){}.getType();
        return modelMapper.map(jiraUserList, targetistType);
    }

    @Override
    public JiraUser fromJiraUserDto(AdminReportDto adminReportDto) {
        return modelMapper.map(adminReportDto, JiraUser.class);
    }

    @Override
    public List<JiraUser> fromJiraUserDtos(List<AdminReportDto> adminReportDtoList) {
        Type targetistType = new TypeToken<List<JiraUser>>(){}.getType();
        return modelMapper.map(adminReportDtoList, targetistType);
    }
}
