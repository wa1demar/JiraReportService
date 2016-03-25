package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraGroupMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class JiraGroupMapperImpl implements JiraGroupMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<JiraGroup> fromDtos(List<JiraGroupDto> groups) {
        Type targetistType = new TypeToken<List<JiraGroup>>(){}.getType();
        return modelMapper.map(groups, targetistType);
    }
}
