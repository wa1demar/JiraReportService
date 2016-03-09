package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamDataDto implements Serializable {
    private Long agileSprintId;
    private String devName;
    private Long targetPoints;
    private Long actualPoints;

    private Long defectMin;
    private Long defectMax;
    private Long defectActual;
    private Long defectTargetHours;
    private Double defectActualHours;

    private Long uatDefectMin;
    private Long uatDefectMax;
    private Long uatDefectActual;
    private Long uatDefectTargetHours;
    private Double uatDefectActualHours;

    public Long getAgileSprintId() {
        return agileSprintId;
    }

    public void setAgileSprintId(Long agileSprintId) {
        this.agileSprintId = agileSprintId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Long getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(Long targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(Long actualPoints) {
        this.actualPoints = actualPoints;
    }

    public Long getDefectMin() {
        return defectMin;
    }

    public void setDefectMin(Long defectMin) {
        this.defectMin = defectMin;
    }

    public Long getDefectMax() {
        return defectMax;
    }

    public void setDefectMax(Long defectMax) {
        this.defectMax = defectMax;
    }

    public Long getDefectActual() {
        return defectActual;
    }

    public void setDefectActual(Long defectActual) {
        this.defectActual = defectActual;
    }

    public Long getDefectTargetHours() {
        return defectTargetHours;
    }

    public void setDefectTargetHours(Long defectTargetHours) {
        this.defectTargetHours = defectTargetHours;
    }

    public Double getDefectActualHours() {
        return defectActualHours;
    }

    public void setDefectActualHours(Double defectActualHours) {
        this.defectActualHours = defectActualHours;
    }

    public Long getUatDefectMin() {
        return uatDefectMin;
    }

    public void setUatDefectMin(Long uatDefectMin) {
        this.uatDefectMin = uatDefectMin;
    }

    public Long getUatDefectMax() {
        return uatDefectMax;
    }

    public void setUatDefectMax(Long uatDefectMax) {
        this.uatDefectMax = uatDefectMax;
    }

    public Long getUatDefectActual() {
        return uatDefectActual;
    }

    public void setUatDefectActual(Long uatDefectActual) {
        this.uatDefectActual = uatDefectActual;
    }

    public Long getUatDefectTargetHours() {
        return uatDefectTargetHours;
    }

    public void setUatDefectTargetHours(Long uatDefectTargetHours) {
        this.uatDefectTargetHours = uatDefectTargetHours;
    }

    public Double getUatDefectActualHours() {
        return uatDefectActualHours;
    }

    public void setUatDefectActualHours(Double uatDefectActualHours) {
        this.uatDefectActualHours = uatDefectActualHours;
    }
}
