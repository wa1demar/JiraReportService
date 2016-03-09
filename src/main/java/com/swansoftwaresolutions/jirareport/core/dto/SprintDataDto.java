package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDataDto implements Serializable {

    private Long id;
    private String state;
    private Long type;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private Long targetPoints;
    private Long allPointsFromAgile;
    private Long actualPoints;
    private Long targetHours;
    private Double actualHours;

    private Long defectMin;
    private Long defectMax;
    private Long defectHours;

    private Long uatDefectMin;
    private Long uatDefectMax;
    private Long uatDefectHours;

    private Long actualQatDefects;
    private Long actualUatDefects;

    private Double actualQatDefectsHours;
    private Double actualUatDefectsHours;

    private Long notCountTarget;

    private Long showUat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Long getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(Long targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getAllPointsFromAgile() {
        return allPointsFromAgile;
    }

    public void setAllPointsFromAgile(Long allPointsFromAgile) {
        this.allPointsFromAgile = allPointsFromAgile;
    }

    public Long getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(Long actualPoints) {
        this.actualPoints = actualPoints;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public Double getActualHours() {
        return actualHours;
    }

    public void setActualHours(Double actualHours) {
        this.actualHours = actualHours;
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

    public Long getDefectHours() {
        return defectHours;
    }

    public void setDefectHours(Long defectHours) {
        this.defectHours = defectHours;
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

    public Long getUatDefectHours() {
        return uatDefectHours;
    }

    public void setUatDefectHours(Long uatDefectHours) {
        this.uatDefectHours = uatDefectHours;
    }

    public Long getActualQatDefects() {
        return actualQatDefects;
    }

    public void setActualQatDefects(Long actualQatDefects) {
        this.actualQatDefects = actualQatDefects;
    }

    public Long getActualUatDefects() {
        return actualUatDefects;
    }

    public void setActualUatDefects(Long actualUatDefects) {
        this.actualUatDefects = actualUatDefects;
    }

    public Double getActualQatDefectsHours() {
        return actualQatDefectsHours;
    }

    public void setActualQatDefectsHours(Double actualQatDefectsHours) {
        this.actualQatDefectsHours = actualQatDefectsHours;
    }

    public Double getActualUatDefectsHours() {
        return actualUatDefectsHours;
    }

    public void setActualUatDefectsHours(Double actualUatDefectsHours) {
        this.actualUatDefectsHours = actualUatDefectsHours;
    }

    public Long getNotCountTarget() {
        return notCountTarget;
    }

    public void setNotCountTarget(Long notCountTarget) {
        this.notCountTarget = notCountTarget;
    }

    public Long getShowUat() {
        return showUat;
    }

    public void setShowUat(Long showUat) {
        this.showUat = showUat;
    }
}
