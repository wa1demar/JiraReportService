package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraGroupMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraGroupRepository;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.GroupImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class GroupImporterServiceImpl implements GroupImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    JiraGroupMapper groupMapper;

    @Autowired
    JiraGroupRepository groupRepository;

    @Override
    public void importGroupsFromJira() {
        JiraGroupsDto jiraGroupsDto = restClient.loadAllGroups();

        List<JiraGroup> groups = groupMapper.fromDtos(jiraGroupsDto.getGroups());
        groupRepository.save(groups);
    }
}
