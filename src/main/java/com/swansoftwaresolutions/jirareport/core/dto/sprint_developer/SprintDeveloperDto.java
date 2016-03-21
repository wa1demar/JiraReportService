package com.swansoftwaresolutions.jirareport.core.dto.sprint_developer;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDeveloperDto {

    private Long id;
    private String developerLogin;
    private String developerName;
    private Long engineerLevel;
    private Double participationLevel;
    private int daysInSprint;
    private Double defectHours;
    private Double defectMax;
    private Double defectMin;
    private Long targetHours;
    private int targetPoints;
    private Double uatDefectHours;
    private Double uatDefectMax;
    private Double uatDefectMin;
    private int actualPoints;
    private int defectActual;
    private Long actualHours;
    private Long defectActualHours;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeveloperLogin() {
        return developerLogin;
    }

    public void setDeveloperLogin(String developerLogin) {
        this.developerLogin = developerLogin;
    }

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

    public Double getDefectHours() {
        return defectHours;
    }

    public void setDefectHours(Double defectHours) {
        this.defectHours = defectHours;
    }

    public Double getDefectMax() {
        return defectMax;
    }

    public void setDefectMax(Double defectMax) {
        this.defectMax = defectMax;
    }

    public Double getDefectMin() {
        return defectMin;
    }

    public void setDefectMin(Double defectMin) {
        this.defectMin = defectMin;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public int getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(int targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Double getUatDefectHours() {
        return uatDefectHours;
    }

    public void setUatDefectHours(Double uatDefectHours) {
        this.uatDefectHours = uatDefectHours;
    }

    public Double getUatDefectMax() {
        return uatDefectMax;
    }

    public void setUatDefectMax(Double uatDefectMax) {
        this.uatDefectMax = uatDefectMax;
    }

    public Double getUatDefectMin() {
        return uatDefectMin;
    }

    public void setUatDefectMin(Double uatDefectMin) {
        this.uatDefectMin = uatDefectMin;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public int getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(int actualPoints) {
        this.actualPoints = actualPoints;
    }

    public Long getActualHours() {
        return actualHours;
    }

    public void setActualHours(Long actualHours) {
        this.actualHours = actualHours;
    }

    public int getDefectActual() {
        return defectActual;
    }

    public void setDefectActual(int defectActual) {
        this.defectActual = defectActual;
    }

    public Long getDefectActualHours() {
        return defectActualHours;
    }

    public void setDefectActualHours(Long defectActualHours) {
        this.defectActualHours = defectActualHours;
    }
}
