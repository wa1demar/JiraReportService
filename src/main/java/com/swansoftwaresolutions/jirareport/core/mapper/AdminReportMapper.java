package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.AdminReport;
import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.rest.dto.AdminReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface AdminReportMapper {

    AdminReportDto toDto(AdminReport adminReport);
    List<AdminReportDto> toDtos(List<AdminReport> adminReportList);
    AdminReport fromDto(AdminReportDto adminReportDto);
    List<AdminReport> fromDtos(List<AdminReportDto> adminReportDtoList);

}
