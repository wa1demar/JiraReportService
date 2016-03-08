package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.entity.AdminReport;
import com.swansoftwaresolutions.jirareport.core.mapper.AdminReportMapper;
import com.swansoftwaresolutions.jirareport.rest.dto.AdminReportDto;
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
    public AdminReportDto toDto(AdminReport adminReport) {
        return modelMapper.map(adminReport, AdminReportDto.class);
    }

    @Override
    public List<AdminReportDto> toDtos(List<AdminReport> adminReportList) {
        Type targetistType = new TypeToken<List<AdminReportDto>>(){}.getType();
        return modelMapper.map(adminReportList, targetistType);
    }

    @Override
    public AdminReport fromDto(AdminReportDto adminReportDto) {
        return modelMapper.map(adminReportDto, AdminReport.class);
    }

    @Override
    public List<AdminReport> fromDtos(List<AdminReportDto> adminReportDtoList) {
        Type targetistType = new TypeToken<List<AdminReport>>(){}.getType();
        return modelMapper.map(adminReportDtoList, targetistType);
    }

}
