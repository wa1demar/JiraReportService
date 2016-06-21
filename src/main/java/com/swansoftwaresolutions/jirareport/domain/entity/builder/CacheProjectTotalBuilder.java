package com.swansoftwaresolutions.jirareport.domain.entity.builder;

import com.swansoftwaresolutions.jirareport.domain.entity.CacheProjectTotal;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class CacheProjectTotalBuilder {

    private CacheProjectTotal projectTotal;

    public CacheProjectTotalBuilder() {
        this.projectTotal = new CacheProjectTotal();
    }

    public CacheProjectTotalBuilder id(Long id) {
        this.projectTotal.setId(id);
        return this;
    }

    public CacheProjectTotalBuilder vTargetPoints(float points) {
        this.projectTotal.setvTargetPoints(points);
        return this;
    }

    public CacheProjectTotalBuilder vTargetHours(Long points) {
        this.projectTotal.setvTargetHours(points);
        return this;
    }

    public CacheProjectTotalBuilder vActualPoints(float points) {
        this.projectTotal.setvActualPoints(points);
        return this;
    }

    public CacheProjectTotalBuilder vActualHours(Long points) {
        this.projectTotal.setvActualHours(points);
        return this;
    }

    public CacheProjectTotalBuilder qtargetMin(int points) {
        this.projectTotal.setQtargetMin(points);
        return this;
    }

    public CacheProjectTotalBuilder qtargetMax(int points) {
        this.projectTotal.setQtargetMax(points);
        return this;
    }

    public CacheProjectTotalBuilder qActualPoints(float points) {
        this.projectTotal.setqActualPoints(points);
        return this;
    }

    public CacheProjectTotalBuilder qTargetHours(Long hours) {
        this.projectTotal.setqTargetHours(hours);
        return this;
    }

    public CacheProjectTotalBuilder qActualHours(Long hours) {
        this.projectTotal.setqActualHours(hours);
        return this;
    }

    public CacheProjectTotalBuilder utargetMin(int points) {
        this.projectTotal.setUtargetMin(points);
        return this;
    }

    public CacheProjectTotalBuilder utargetMax(int points) {
        this.projectTotal.setUtargetMax(points);
        return this;
    }

    public CacheProjectTotalBuilder uActualPoints(float points) {
        this.projectTotal.setuActualPoints(points);
        return this;
    }

    public CacheProjectTotalBuilder uTargetHours(Long hours) {
        this.projectTotal.setuTargetHours(hours);
        return this;
    }

    public CacheProjectTotalBuilder uActualHours(Long hours) {
        this.projectTotal.setuActualHours(hours);
        return this;
    }

    public CacheProjectTotalBuilder chartActual(Integer[] data) {
        this.projectTotal.setChartActual(String.join(",", Arrays.toString(data)));
        return this;
    }

    public CacheProjectTotalBuilder chartTarget(Double[] data) {
        this.projectTotal.setChartTarget(String.join(",", Arrays.toString(data)));
        return this;
    }

    public CacheProjectTotalBuilder chartLabels(String[] data) {
        this.projectTotal.setChartLabels(String.join(",", data));
        return this;
    }

    public CacheProjectTotalBuilder report(Report data) {
        this.projectTotal.setReport(data);
        return this;
    }

    public CacheProjectTotalBuilder showUat(boolean showUat) {
        this.projectTotal.setShowUat(showUat);
        return this;
    }

    public CacheProjectTotalBuilder sprintsCount(long count) {
        this.projectTotal.setSprintsCount(count);
        return this;
    }

    public CacheProjectTotalBuilder closedSprintsCount(long count) {
        this.projectTotal.setClosedSprintCount(count);
        return this;
    }

    public CacheProjectTotalBuilder issues(String issues) {
        this.projectTotal.setIssues(issues);
        return this;
    }

    public CacheProjectTotalBuilder description(String description) {
        this.projectTotal.setDescription(description);
        return this;
    }

    public CacheProjectTotalBuilder startDate(Date date) {
        this.projectTotal.setStartDate(date);
        return this;
    }

    public CacheProjectTotalBuilder endDate(Date date) {
        this.projectTotal.setEndDate(date);
        return this;
    }

    public CacheProjectTotal build() {
        return projectTotal;
    }


}
