package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraBoardDto;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraBoardInfoDto;

import java.util.List;

/**
 * @author Vitaliy Hollovko
 */
public interface JiraBoardMapper {

    JiraBoardDto toDto(JiraBoard jiraBoard);
    List<JiraBoardDto> toDtos(List<JiraBoard> jiraBoardList);
    JiraBoard fromDto(JiraBoardDto jiraBoardDto);
    List<JiraBoard> fromDtos(List<JiraBoardDto> jiraBoardDtoList);

    JiraBoardInfoDto toInfoDto(JiraBoard jiraBoard);
    List<JiraBoardInfoDto> toInfoDtos(List<JiraBoard> jiraBoardList);
    JiraBoard fromInfoDto(JiraBoardInfoDto jiraBoardInfoDto);
    List<JiraBoard> fromInfoDtos(List<JiraBoardInfoDto> jiraBoardInfoDtoList);
}
