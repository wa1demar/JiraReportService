package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.ReportDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
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
