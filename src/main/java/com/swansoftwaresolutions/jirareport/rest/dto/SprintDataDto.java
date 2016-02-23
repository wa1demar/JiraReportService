package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDataDto implements Serializable {
    public Long id;
    public String state;
    public Long type;
    public String name;
    public Date startDate;
    public Date endDate;
    public Date completeDate;
    public Long targetPoints;
    public Long allPointsFromAgile;
    public Long actualPoints;
    public Long targetHours;
    public Double actualHours;

    public Long defectMin;
    public Long defectMax;
    public Long defectHours;

    public Long uatDefectMin;
    public Long uatDefectMax;
    public Long uatDefectHours;

    public Long actualQatDefects;
    public Long actualUatDefects;

    public Double actualQatDefectsHours;
    public Double actualUatDefectsHours;

    public Long notCountTarget;
    public List<Double> chartLabel;
    public List<Double> chartTarget;
    public List<Double> chartActual;

    public Long showUat;
}
