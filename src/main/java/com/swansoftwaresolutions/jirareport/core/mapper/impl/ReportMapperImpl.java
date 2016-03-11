package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;
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
public class ReportMapperImpl implements ReportMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Report fromDto(NewReportDto newReportDto) {
        return modelMapper.map(newReportDto, Report.class);
    }

    @Override
    public ReportDto toDto(Report report) {
        return modelMapper.map(report, ReportDto.class);
    }

    @Override
    public List<ReportDto> toDtos(List<Report> reportList) {
        Type targetistType = new TypeToken<List<ReportDto>>(){}.getType();
        return modelMapper.map(reportList, targetistType);
    }

    @Override
    public Report fromDto(ReportDto reportDto) {
        return modelMapper.map(reportDto, Report.class);
    }

    @Override
    public List<Report> fromDtos(List<ReportDto> reportDtos) {
        Type targetistType = new TypeToken<List<Report>>(){}.getType();
        return modelMapper.map(reportDtos, targetistType);
    }

    @Override
    public ReportResponceDto toResponceDto(ReportDto reportDto) {
        ReportResponceDto reportResponceDto = modelMapper.map(reportDto, ReportResponceDto.class);
//        reportResponceDto.setAdmins(reportDto.getAdmins());

        return reportResponceDto;
    }
}
