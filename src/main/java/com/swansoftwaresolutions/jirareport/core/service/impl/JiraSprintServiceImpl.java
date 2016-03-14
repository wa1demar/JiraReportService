package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraSprintServiceImpl implements JiraSprintsService {

    @Autowired
    SprintRepository sprintRepository;

    @Autowired
    SprintMapper sprintMapper;

    @Override
    public SprintsDto retrieveByReportId(long reportId) {
        List<Sprint> sprints = sprintRepository.findByReportId(reportId);

        return sprintMapper.toDto(sprints);
    }

    @Override
    public void add(Sprint sprint) {

    }

//    public Map<String, Integer> getTargetDataBySprint(Long idSprint, Long idReport) {
//        Map<String, Integer> result = new HashMap<>();
//
//        int targetPoints = 0;
//        int targetHours = 0;
//
//        int defectMin = 0;
//        int defectMax = 0;
//        int defectHours = 0;
//
//        int uatDefectMin = 0;
//        int uatDefectMax = 0;
//        int uatDefectHours = 0;
//
//        List<SprintTeamDto> sprintTeams = DaoFactory.getInstance().getSprintTeamDao().getSprintTeamByIdReportAndIdAgileSprint(idReport, idSprint);
//
//        if (sprintTeams.size() > 0) {
//            for (SprintTeamDto value : sprintTeams) {
//                targetPoints += value.getTargetPoints();
//                targetHours += value.getTargetHours();
//
//                defectMin += value.getDefectMin();
//                defectMax += value.getDefectMax();
//                defectHours += value.getDefectHours();
//
//                uatDefectMin += value.getUatDefectMin();
//                uatDefectMax += value.getUatDefectMax();
//                uatDefectHours += value.getUatDefectHours();
//            }
//        }
//
//        result.put("targetPoints", targetPoints);
//        result.put("targetHours", targetHours);
//
//        result.put("defectMin", defectMin);
//        result.put("defectMax", defectMax);
//        result.put("defectHours", defectHours);
//
//        result.put("uatDefectMin", uatDefectMin);
//        result.put("uatDefectMax", uatDefectMax);
//        result.put("uatDefectHours", uatDefectHours);
//
//        return result;
//    }

    @Override
    public SprintDto add(SprintDto sprint) {
        Sprint sprintDto = sprintMapper.fromDto(sprint);
        Sprint newSprint = sprintRepository.add(sprintDto);
        return sprintMapper.toDto(newSprint);
    }

    @Override
    public List<Sprint> findAll() {
        return sprintRepository.findAll();
    }

    @Override
    public void delete(Sprint sprint) throws NoSuchEntityException {
        sprintRepository.delete(sprint);
    }

    @Override
    public void delete(Long sprintId) throws NoSuchEntityException {
        sprintRepository.delete(sprintId);
    }
}
