package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "cache_projects_total")
public class CacheProjectTotal implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "velosity_target_points")
    private float vTargetPoints;

    @Column(name = "velosity_actual_points")
    private float vActualPoints;

    @Column(name = "velosity_target_hours")
    private long vTargetHours;

    @Column(name = "velosity_actual_hours")
    private long vActualHours;

    @Column(name = "qat_target_min")
    private int qtargetMin;

    @Column(name = "qat_target_max")
    private int qtargetMax;

    @Column(name = "qat_actual_points")
    private float qActualPoints;

    @Column(name = "qat_target_hours")
    private long qTargetHours;

    @Column(name = "qat_actual_hours")
    private long qActualHours;

    @Column(name = "uat_target_min")
    private int utargetMin;

    @Column(name = "uat_target_max")
    private int utargetMax;

    @Column(name = "uat_actual_points")
    private float uActualPoints;

    @Column(name = "uat_target_hours")
    private long uTargetHours;

    @Column(name = "uat_actual_hours")
    private long uActualHours;

    @Column(name = "chart_actual")
    private String chartActual;

    @Column(name = "chart_target")
    private String chartTarget;

    @Column(name = "chart_labels")
    private String chartLabels;

    @Column(name = "closed_sprints_count")
    private long closedSprintCount;

    @Column(name = "show_uat")
    private boolean showUat;

    @Column(name = "sprints_count")
    private long sprintsCount;

    @Column(name = "issues")
    private String issues;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name="report_id")
    private Report report;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getvTargetPoints() {
        return vTargetPoints;
    }

    public void setvTargetPoints(float vTargetPoints) {
        this.vTargetPoints = vTargetPoints;
    }

    public float getvActualPoints() {
        return vActualPoints;
    }

    public void setvActualPoints(float vActualPoints) {
        this.vActualPoints = vActualPoints;
    }

    public int getQtargetMin() {
        return qtargetMin;
    }

    public void setQtargetMin(int qtargetMin) {
        this.qtargetMin = qtargetMin;
    }

    public int getQtargetMax() {
        return qtargetMax;
    }

    public void setQtargetMax(int qtargetMax) {
        this.qtargetMax = qtargetMax;
    }

    public float getqActualPoints() {
        return qActualPoints;
    }

    public void setqActualPoints(float qActualPoints) {
        this.qActualPoints = qActualPoints;
    }

    public long getqTargetHours() {
        return qTargetHours;
    }

    public void setqTargetHours(long qTargetHours) {
        this.qTargetHours = qTargetHours;
    }

    public long getqActualHours() {
        return qActualHours;
    }

    public void setqActualHours(long qActualHours) {
        this.qActualHours = qActualHours;
    }

    public int getUtargetMin() {
        return utargetMin;
    }

    public void setUtargetMin(int utargetMin) {
        this.utargetMin = utargetMin;
    }

    public int getUtargetMax() {
        return utargetMax;
    }

    public void setUtargetMax(int utargetMax) {
        this.utargetMax = utargetMax;
    }

    public float getuActualPoints() {
        return uActualPoints;
    }

    public void setuActualPoints(float uActualPoints) {
        this.uActualPoints = uActualPoints;
    }

    public long getuTargetHours() {
        return uTargetHours;
    }

    public void setuTargetHours(long uTargetHours) {
        this.uTargetHours = uTargetHours;
    }

    public long getuActualHours() {
        return uActualHours;
    }

    public void setuActualHours(long uActualHours) {
        this.uActualHours = uActualHours;
    }

    public String getChartActual() {
        return chartActual;
    }

    public void setChartActual(String chartActual) {
        this.chartActual = chartActual;
    }

    public String getChartTarget() {
        return chartTarget;
    }

    public void setChartTarget(String chartTarget) {
        this.chartTarget = chartTarget;
    }

    public String getChartLabels() {
        return chartLabels;
    }

    public void setChartLabels(String chartLabels) {
        this.chartLabels = chartLabels;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public long getvTargetHours() {
        return vTargetHours;
    }

    public void setvTargetHours(long vTargetHours) {
        this.vTargetHours = vTargetHours;
    }

    public long getvActualHours() {
        return vActualHours;
    }

    public void setvActualHours(long vActualHours) {
        this.vActualHours = vActualHours;
    }

    public long getClosedSprintCount() {
        return closedSprintCount;
    }

    public void setClosedSprintCount(long closedSprintCount) {
        this.closedSprintCount = closedSprintCount;
    }

    public boolean isShowUat() {
        return showUat;
    }

    public void setShowUat(boolean showUat) {
        this.showUat = showUat;
    }

    public long getSprintsCount() {
        return sprintsCount;
    }

    public void setSprintsCount(long sprintsCount) {
        this.sprintsCount = sprintsCount;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
