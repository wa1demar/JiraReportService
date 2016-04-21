package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.helper.HelperMethods;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraIssueMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraSprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.IssueImporterService;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssueDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssuesDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Service
public class IssueImporterServiceImpl implements IssueImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    JiraIssueMapper issueMapper;

    @Autowired
    JiraIssueRepository issueRepository;

    @Autowired
    JiraSprintRepository sprintRepository;

    @Override
    public void importIssueFromJira() {
        HelperMethods hm = new HelperMethods();
        try {
            List<JiraSprint> sprints = sprintRepository.findAll();

            for (JiraSprint sprint :sprints) {
                IssuesDto issues = null;
                try {
                    issues = restClient.loadAllIssues(String.valueOf(sprint.getSprintId()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (issues != null) {
                    List<JiraIssueDto> list = new ArrayList<>();
                    for (IssueDto issueDto : issues.issues) {
                        JiraIssueDto jiraIssueDto = hm.convertIssueDtoToJiraIssueDto(issueDto, sprint.getBoardId());
                        jiraIssueDto.setBoardId(sprint.getBoardId());
                        jiraIssueDto.setSprintId(sprint.getId());
                        list.add(jiraIssueDto);
                    }

                    issueRepository.saveAll(issueMapper.fromDtos(list));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
