package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "cache_projects_total")
public class CacheProjectTotal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "velosity_target_points")
    private float vTargetPoints;

    @Column(name = "velosity_actual_points")
    private float vActualPoints;

    @Column(name = "velosity_target_hours")
    private Long vTargetHours;

    @Column(name = "velosity_actual_hours")
    private Long vActualHours;

    @Column(name = "qat_target_min")
    private int qtargetMin;

    @Column(name = "qat_target_max")
    private int qtargetMax;

    @Column(name = "qat_actual_points")
    private float qActualPoints;

    @Column(name = "qat_target_hours")
    private Long qTargetHours;

    @Column(name = "qat_actual_hours")
    private Long qActualHours;

    @Column(name = "uat_target_min")
    private int utargetMin;

    @Column(name = "uat_target_max")
    private int utargetMax;

    @Column(name = "uat_actual_points")
    private float uActualPoints;

    @Column(name = "uat_target_hours")
    private Long uTargetHours;

    @Column(name = "uat_actual_hours")
    private Long uActualHours;

    @Column(name = "chart_actual")
    private String chartActual;

    @Column(name = "chart_target")
    private String chartTarget;

    @Column(name = "chart_labels")
    private String chartLabels;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="report_id")
    private Report report;

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

    public Long getqTargetHours() {
        return qTargetHours;
    }

    public void setqTargetHours(Long qTargetHours) {
        this.qTargetHours = qTargetHours;
    }

    public Long getqActualHours() {
        return qActualHours;
    }

    public void setqActualHours(Long qActualHours) {
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

    public Long getuTargetHours() {
        return uTargetHours;
    }

    public void setuTargetHours(Long uTargetHours) {
        this.uTargetHours = uTargetHours;
    }

    public Long getuActualHours() {
        return uActualHours;
    }

    public void setuActualHours(Long uActualHours) {
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

    public Long getvTargetHours() {
        return vTargetHours;
    }

    public void setvTargetHours(Long vTargetHours) {
        this.vTargetHours = vTargetHours;
    }

    public Long getvActualHours() {
        return vActualHours;
    }

    public void setvActualHours(Long vActualHours) {
        this.vActualHours = vActualHours;
    }
}
