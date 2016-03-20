package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.JiraPointDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.PointService;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.sheduller.dto.*;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Component("issueRestClient")
public class IssueRestClient extends RestClientBase implements RestClient {

    static Logger log = Logger.getLogger(IssueRestClient.class.getName());

    final String URL_SPRINT = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/{boardId}/sprint";
    final String URL_ISSUES_BY_SPRINT = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/sprint/{sprintId}/issue?maxResults=500";


    @Autowired
    JiraBoardService jiraBoardService;

    @Autowired
    JiraUserService jiraUserService;

    @Autowired
    PointService pointService;

    @Autowired
    ReportService reportService;

    @Override
    public void loadData() {
        List<JiraBoard> jiraBoardList = null;
        try {
            jiraBoardList = jiraBoardService.findAll();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        combineInformationForPoint(jiraBoardList);
    }

    private void combineInformationForPoint(List<JiraBoard> jiraBoardList) {
        for (JiraBoard jiraBoard : jiraBoardList) {
            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplate();
            AgileSprintsDto agileSprints = restTemplate.exchange(URL_SPRINT.replace("{boardId}", String.valueOf(jiraBoard.getBoardId())), HttpMethod.GET, request, AgileSprintsDto.class).getBody();

            try {
                getAllIsuues(agileSprints);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAllIsuues(AgileSprintsDto agileSprints) throws NoSuchEntityException {
        for (AgileSprintDto agileSprintDto : agileSprints.values) {
            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplate();

            IssuesDto issues = restTemplate.exchange(URL_ISSUES_BY_SPRINT.replace("{sprintId}", String.valueOf(agileSprintDto.id)), HttpMethod.GET, request, IssuesDto.class).getBody();
            List<JiraIssueDto> jiraIssueList = new ArrayList<>();
            for (IssueDto issueDto : issues.issues) {
                jiraIssueList.add(convertIssueDtoToJiraIssueDto(issueDto));
            }

//            ReportDto report = new ReportDto();
//            try {
//                report = reportService.findByBoardId(new Long(agileSprintDto.originBoardId));
//            } catch (NoSuchEntityException ex){
//                log.warning("NoSuchEntityException Report");
//            }

            Map<String, List<JiraIssueDto>> mapPoints = grupByUser(jiraIssueList);

            List<JiraPointDto> points = new ArrayList<>();

            for (Map.Entry<String, List<JiraIssueDto>> entry : mapPoints.entrySet()) {
                String key = entry.getKey();
                List<JiraIssueDto> thing = entry.getValue();

                JiraPointDto jiraPointDto = new JiraPointDto();
                jiraPointDto.setUserLogin(key);
                jiraPointDto.setSprintId(agileSprintDto.id);
                try {

                    for (JiraIssueDto jiraIssueDto : thing) {
                        if (!jiraIssueDto.getIssueTypeName().equals("Bug")) {
                            jiraPointDto.setIssueCount(jiraPointDto.getIssueCount()+1);
                            jiraPointDto.setIssueHourse(checkNotNull(jiraPointDto.getIssueHourse())+checkNotNull(jiraIssueDto.getTimeSpent()));
                            jiraPointDto.setPoints(jiraPointDto.getPoints() +jiraIssueDto.getPoints());
                        } else {
//                            if (report!=null && checkAdmin(report.getAdmins(), jiraIssueDto.getCreatorName())){
//                                pointDto.setBugUATCount(pointDto.getBugQATCount()+1);
//                                pointDto.setBugUATHours(checkNotNull(pointDto.getBugUATHours()) + checkNotNull(jiraIssueDto.getTimeSpent()));
//                            } else {
                                jiraPointDto.setBugQATCount(jiraPointDto.getBugQATCount() + 1);
                                jiraPointDto.setBugQATHourse(checkNotNull(jiraPointDto.getBugQATHourse())+checkNotNull(jiraIssueDto.getTimeSpent()));
//                            }

                        }
                    }

                } catch (NullPointerException ec) {
                    log.warning("!!!!!!!!!!");
                } catch (Exception sj) {
                    log.warning("!!!!!!!!!!");
                }
                points.add(jiraPointDto);
                jiraPointDto = pointService.add(jiraPointDto);
            }

            log.info("completed " + points.size());


        }

    }

    private boolean checkAdmin(List<JiraUserDto> admins, String creatorName) {
        for(JiraUserDto jiraIssueDto: admins){
            if (jiraIssueDto.getLogin().equals(creatorName)){
                return true;
            }
        }
        return false;
    }

    private Long checkNotNull(Long timeSpent) {
        if (timeSpent == null) {
            return 0L;
        }
        return timeSpent;
    }

    private Map<String, List<JiraIssueDto>> grupByUser(List<JiraIssueDto> jiraIssueList) {
        Map<String, List<JiraIssueDto>> mapPoints = new HashMap<>();
        for (JiraIssueDto jiraIssue : jiraIssueList) {
            if (mapPoints.containsKey(jiraIssue.getAssignetName())) {
                List<JiraIssueDto> list = mapPoints.get(jiraIssue.getAssignetName());
                list.add(jiraIssue);
                mapPoints.put(jiraIssue.getAssignetName(), list);
            } else {
                List<JiraIssueDto> list = new ArrayList<>();
                list.add(jiraIssue);

                mapPoints.put(jiraIssue.getAssignetName(), list);
            }
        }

        return mapPoints;
    }

    private JiraIssueDto convertIssueDtoToJiraIssueDto(IssueDto issueDto) {
        JiraIssueDto jiraIssueDto = new JiraIssueDto();
        jiraIssueDto.setKey(issueDto.key);
        jiraIssueDto.setProjectId(issueDto.fields.project.getId());
        jiraIssueDto.setProjectKey(issueDto.fields.project.getKey());
        jiraIssueDto.setIssueTypeId(issueDto.fields.issuetype.id);
        jiraIssueDto.setIssueTypeName(issueDto.fields.issuetype.name);
        jiraIssueDto.setIssueTypeSubTask(issueDto.fields.issuetype.subtask);
        jiraIssueDto.setTimeSpent(issueDto.fields.timespent);
        jiraIssueDto.setResolutionId(issueDto.fields.resolution.id);
        jiraIssueDto.setResolutionName(issueDto.fields.resolution.name);
        jiraIssueDto.setCreated(issueDto.fields.created);
        jiraIssueDto.setUpdated(issueDto.fields.updated);
        jiraIssueDto.setAssignetKey(issueDto.fields.assignee.key);
        jiraIssueDto.setAssignetName(issueDto.fields.assignee.name);
        jiraIssueDto.setAssignetFullName(issueDto.fields.assignee.displayName);
        jiraIssueDto.setCreatorKey(issueDto.fields.creator.key);
        jiraIssueDto.setCreatorName(issueDto.fields.creator.name);
        jiraIssueDto.setCreatorFullName(issueDto.fields.creator.displayName);
        jiraIssueDto.setStatusId(issueDto.fields.status.id);
        jiraIssueDto.setStatusName(issueDto.fields.status.name);
        jiraIssueDto.setDueDate(issueDto.fields.duedate);
        jiraIssueDto.setPoints(issueDto.fields.customfield_10102);
        return jiraIssueDto;
    }

}
