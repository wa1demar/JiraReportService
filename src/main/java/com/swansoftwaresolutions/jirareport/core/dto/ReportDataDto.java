package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportDataDto implements Serializable {

    private ReportDto report;

    private Long percentPoints;
    private Long percentHours;

    private List<Double> chartLabel;
    private List<Double> chartTarget;
    private List<Double> chartActual;

    private Long showUat;
    private Long closedSprintCount;

    public ReportDto getReport() {
        return report;
    }

    public void setReport(ReportDto report) {
        this.report = report;
    }

    public Long getPercentPoints() {
        return percentPoints;
    }

    public void setPercentPoints(Long percentPoints) {
        this.percentPoints = percentPoints;
    }

    public Long getPercentHours() {
        return percentHours;
    }

    public void setPercentHours(Long percentHours) {
        this.percentHours = percentHours;
    }

    public List<Double> getChartLabel() {
        return chartLabel;
    }

    public void setChartLabel(List<Double> chartLabel) {
        this.chartLabel = chartLabel;
    }

    public List<Double> getChartTarget() {
        return chartTarget;
    }

    public void setChartTarget(List<Double> chartTarget) {
        this.chartTarget = chartTarget;
    }

    public List<Double> getChartActual() {
        return chartActual;
    }

    public void setChartActual(List<Double> chartActual) {
        this.chartActual = chartActual;
    }

    public Long getShowUat() {
        return showUat;
    }

    public void setShowUat(Long showUat) {
        this.showUat = showUat;
    }

    public Long getClosedSprintCount() {
        return closedSprintCount;
    }

    public void setClosedSprintCount(Long closedSprintCount) {
        this.closedSprintCount = closedSprintCount;
    }
}
