package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface ReportMapper {

    ReportDto toDto(Report report);
    List<ReportDto> toDtos(List<Report> reportList);
    Report fromDto(ReportDto reportDto);
    List<Report> fromDtos(List<ReportDto> reportDto);

    NewReportDto toNewDto(Report report);
    List<NewReportDto> toNewDtos(List<Report> reportList);
    Report fromNewDto(NewReportDto reportDto);
    List<Report> fromNewDtos(List<NewReportDto> reportDto);
}
