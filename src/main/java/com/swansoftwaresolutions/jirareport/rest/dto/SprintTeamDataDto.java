package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamDataDto implements Serializable {
    public Long agileSprintId;
    public String devName;
    public Long targetPoints;
    public Long actualPoints;

    public Long defectMin;
    public Long defectMax;
    public Long defectActual;
    public Long defectTargetHours;
    public Double defectActualHours;

    public Long uatDefectMin;
    public Long uatDefectMax;
    public Long uatDefectActual;
    public Long uatDefectTargetHours;
    public Double uatDefectActualHours;
}
