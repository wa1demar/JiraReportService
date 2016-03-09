package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import com.swansoftwaresolutions.jirareport.core.dto.SprintDataDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintTeamDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintsDto;
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
    public SprintsDto retrieveByReportId(long reportId, int typeId) {

//        List<Sprint> sprints = sprintRepository.findSprintsByReportId(reportId);
//
//        List<SprintDataDto> dataLocal = new ArrayList<>();
//
//        for (Sprint sprint : sprints) {
//            Map<String, Double> sprintActualPoints;
//
//            Map<String, Integer> sprintTargetPoints = getTargetDataBySprint(sprint.getAgileSprintId(), reportId);
//            if (typeId == 0 || typeId == 1) {
//                sprintActualPoints = getActualDataFromSprintData(sprint.getId(), reportId);
//            } else {
//                sprintActualPoints = getActualDataFromManualSprintData(sprint.getId());
//            }
//
//            //System.out.println(sprintActualPoints);
//
//            dataLocal.add(new SprintDataDto(sprint.getAgileSprintId(), sprint.getState().toLowerCase(), sprint.getType(),
//                    sprint.getName(),
//                    sprint.getStartDate(),
//                    sprint.getEndDate(),
//                    sprint.getCompleteDate(),
//                    sprintTargetPoints.get("targetPoints"),
//                    Math.round(sprintActualPoints.get("allPointsFromAgile")),
//                    Math.round(sprintActualPoints.get("actualPoints")),
//                    sprintTargetPoints.get("targetHours"),
//                    Math.round(sprintActualPoints.get("actualHours") * 10) / 10.0,
//
//                    sprintTargetPoints.get("defectMin"),
//                    sprintTargetPoints.get("defectMax"),
//                    sprintTargetPoints.get("defectHours"),
//
//                    sprintTargetPoints.get("uatDefectMin"),
//                    sprintTargetPoints.get("uatDefectMax"),
//                    sprintTargetPoints.get("uatDefectHours"),
//
//                    Math.round(sprintActualPoints.get("actualQatDefects")),
//                    sprint.getShowUat() == 1 ? Math.round(sprintActualPoints.get("actualUatDefects")) : 0,
//
//                    Math.round(sprintActualPoints.get("actualQatDefectsHours") * 10) / 10.0,
//                    sprint.getShowUat() == 1 ? Math.round(sprintActualPoints.get("actualUatDefectsHours") * 10) / 10.0 : 0,
//
//                    sprint.getNotCountTarget(),
//
//                    sprint.getShowUat()
//            ));
//        }
//
        SprintsDto result = new SprintsDto();
//        result.setValues(dataLocal);

        return result;
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
    public Sprint save(Sprint sprint) {
        return sprintRepository.add(sprint);
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
