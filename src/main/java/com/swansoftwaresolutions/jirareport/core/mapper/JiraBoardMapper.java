package com.swansoftwaresolutions.jirareport.core.mapper;


import com.swansoftwaresolutions.jirareport.core.dto.ImportedBardsDto;
import com.swansoftwaresolutions.jirareport.core.dto.ImportedJiraBoardDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;

import java.util.List;

/**
 * @author Vitaliy Hollovko
 */
public interface JiraBoardMapper {

    JiraBoardDto toDto(JiraBoard jiraBoard);
    List<JiraBoardDto> toDtos(List<JiraBoard> jiraBoardList);
    JiraBoard fromDto(JiraBoardDto jiraBoardDto);
    List<JiraBoard> fromDtos(List<JiraBoardDto> jiraBoardDtoList);
    List<JiraBoard> fromImportedDtos(List<ImportedJiraBoardDto> jiraBoardDtoList);

    JiraBoardInfoDto toInfoDto(JiraBoard jiraBoard);
    List<JiraBoardInfoDto> toInfoDtos(List<JiraBoard> jiraBoardList);
    JiraBoard fromInfoDto(JiraBoardInfoDto jiraBoardInfoDto);
    List<JiraBoard> fromInfoDtos(List<JiraBoardInfoDto> jiraBoardInfoDtoList);
}
