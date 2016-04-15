package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.helper.HelperMethods;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.domain.entity.DueDate;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.DueDateRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraIssueRepository;
import com.swansoftwaresolutions.jirareport.rest.service.DueDateImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Vitaliy Holovko
 */

@Service
public class DueDateImporterServiceImpl implements DueDateImporterService {

    @Autowired
    DueDateRepository dueDateRepository;

    @Autowired
    JiraIssueRepository jiraIssueRepository;

    @Autowired
    ConfigService configService;


    @Override
    public void importDueDate() {

        HelperMethods hp = new HelperMethods();

        String agileDoneName = configService.retrieveConfig().getAgileDoneName();
        List<String> agileDoneNames = new ArrayList<>();
        agileDoneNames.addAll(Arrays.asList(agileDoneName.split(",")));

        List<JiraIssue> jiraIssueList = jiraIssueRepository.findAllDueDate(agileDoneNames);

        List<DueDate> dueDateList = dueDateRepository.findAll();

        Map<Long, List<DueDate>> mapDueDate = generateMap(dueDateList);



        for (JiraIssue issue : jiraIssueList){
            List<DueDate> existed = mapDueDate.get(issue.getId());
            if (existed == null || existed.size()==0){
                addDewDate(issue);
            } else {
                Collections.sort(existed, (o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()));

                DueDate lastExisted = existed.get(0);
                if (!hp.isSameDate(lastExisted.getUpdatedAt(), issue.getUpdated()) && !hp.isSameDate(lastExisted.getDueDate(), issue.getDueDate())){
                    addDewDate(issue);
                }
            }
        }


    }

    private void addDewDate(JiraIssue issue) {
        DueDate dueDate = new DueDate();
        dueDate.setIssue(issue);
        dueDate.setIssueKey(issue.getKey());
        dueDate.setDueDate(issue.getDueDate());
        dueDate.setUpdatedAt(issue.getUpdated());

        dueDateRepository.add(dueDate);
    }

    private Map<Long, List<DueDate>> generateMap(List<DueDate> dueDateList) {
        Map<Long, List<DueDate>> result = new HashMap<>();

        for (DueDate i : dueDateList) {
            if (result.get(i.getIssue().getId()) == null) {
                List<DueDate> devs = new ArrayList<>();
                devs.add(i);

                result.put(i.getIssue().getId(), devs);
            } else {
                List<DueDate> devs = result.get(i.getIssue().getId());
                devs.add(i);
                result.put(i.getIssue().getId(), devs);
            }
        }


        return result;
    }
}
