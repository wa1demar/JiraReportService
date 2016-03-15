package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "sprints")
public class Sprint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "show_uat")
    private boolean showUAT;

    @Column(name = "type")
    private int type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jira_sprint_id")
    private JiraSprint jiraSprint;

    @Column(name = "not_count_target")
    private boolean notCountTarget;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report = new Report();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public boolean isShowUAT() {
        return showUAT;
    }

    public void setShowUAT(boolean showUAT) {
        this.showUAT = showUAT;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public JiraSprint getJiraSprint() {
        return jiraSprint;
    }

    public void setJiraSprint(JiraSprint jiraSprint) {
        this.jiraSprint = jiraSprint;
    }

    public boolean isNotCountTarget() {
        return notCountTarget;
    }

    public void setNotCountTarget(boolean notCountTarget) {
        this.notCountTarget = notCountTarget;
    }
}
