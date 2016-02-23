package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamDto implements Serializable {
    public Long id;
    public Long reportId;
    public Long agileSprintId;
    public String devName;
    public Long engineerLevel;
    public Double participationLevel;
    public Long daysInSprint;

    public Long targetPoints;
    public Long targetHours;

    public Long defectMin;
    public Long defectMax;
    public Long defectHours;

    public Long uatDefectMin;
    public Long uatDefectMax;
    public Long uatDefectHours;
}
