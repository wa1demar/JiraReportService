package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface ReportMapper {

    ReportDto toDto(Report report);
    List<ReportDto> toDtos(List<Report> reportList);
    Report fromDto(ReportDto reportDto);
    List<Report> fromDtos(List<ReportDto> reportDto);

    ReportResponceDto toResponceDto(ReportDto reportDto);

    Report fromDto(NewReportDto newReportDto);
}
