package com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard;

/**
 * @author Vitaliy Holovko
 */
public class SprintTeamDto {
    private String devName;
    private Long engineerLevel;
    private Double participationLevel;
    private int daysInSprint;
    private float targetPoints;
    private float actualPoints;
    private int defectMin;
    private int defectMax;
    private int defectActual;
    private Long defectTargetHours;
    private Long defectActualHours;
    private int uatDefectMin;
    private int uatDefectMax;
    private int uatDefectActual;
    private Long uatDefectTargetHours;
    private Long uatDefectActualHours;


    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

//    public String getEngineerLevel() {
//        return engineerLevel;
//    }
//
//    public void setEngineerLevel(String engineerLevel) {
//        this.engineerLevel = engineerLevel;
//    }


    public Long getEngineerLevel() {
        return engineerLevel;
    }

    public void setEngineerLevel(Long engineerLevel) {
        this.engineerLevel = engineerLevel;
    }

    public Double getParticipationLevel() {
        return participationLevel;
    }

    public void setParticipationLevel(Double participationLevel) {
        this.participationLevel = participationLevel;
    }

    public int getDaysInSprint() {
        return daysInSprint;
    }

    public void setDaysInSprint(int daysInSprint) {
        this.daysInSprint = daysInSprint;
    }

    public float getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(float targetPoints) {
        this.targetPoints = targetPoints;
    }

    public float getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(float actualPoints) {
        this.actualPoints = actualPoints;
    }

    public int getDefectMin() {
        return defectMin;
    }

    public void setDefectMin(int defectMin) {
        this.defectMin = defectMin;
    }

    public int getDefectMax() {
        return defectMax;
    }

    public void setDefectMax(int defectMax) {
        this.defectMax = defectMax;
    }

    public int getDefectActual() {
        return defectActual;
    }

    public void setDefectActual(int defectActual) {
        this.defectActual = defectActual;
    }

    public Long getDefectTargetHours() {
        return defectTargetHours;
    }

    public void setDefectTargetHours(Long defectTargetHours) {
        this.defectTargetHours = defectTargetHours;
    }

    public Long getDefectActualHours() {
        return defectActualHours;
    }

    public void setDefectActualHours(Long defectActualHours) {
        this.defectActualHours = defectActualHours;
    }

    public int getUatDefectMin() {
        return uatDefectMin;
    }

    public void setUatDefectMin(int uatDefectMin) {
        this.uatDefectMin = uatDefectMin;
    }

    public int getUatDefectMax() {
        return uatDefectMax;
    }

    public void setUatDefectMax(int uatDefectMax) {
        this.uatDefectMax = uatDefectMax;
    }

    public int getUatDefectActual() {
        return uatDefectActual;
    }

    public void setUatDefectActual(int uatDefectActual) {
        this.uatDefectActual = uatDefectActual;
    }

    public Long getUatDefectTargetHours() {
        return uatDefectTargetHours;
    }

    public void setUatDefectTargetHours(Long uatDefectTargetHours) {
        this.uatDefectTargetHours = uatDefectTargetHours;
    }

    public Long getUatDefectActualHours() {
        return uatDefectActualHours;
    }

    public void setUatDefectActualHours(Long uatDefectActualHours) {
        this.uatDefectActualHours = uatDefectActualHours;
    }
}
