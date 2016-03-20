package com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard;

import java.util.Date;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class SprintProjectReportDto {
    private Long id;
    private Long reportId;
    private Long agileSprintId;
    private boolean notCountTarget;
    private String name;
    private String state;
    private int type;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private boolean showUat;
    private boolean closed;
    private float targetPoints;
    private Long targetHours;
    private int targetQatDefectMin;
    private int targetQatDefectMax;
    private Long targetQatDefectHours;
    private int targetUatDefectMin;
    private int targetUatDefectMax;
    private Long targetUatDefectHours;
    private float actualPoints;
    private Long actualHours;
    private float actualQatDefectPoints;
    private Long actualQatDefectHours;
    private float actualUatDefectPoints;
    private Long actualUatDefectHours;

    private Chart chart;

    private List<SprintTeamDto> sprintTeam;

}
