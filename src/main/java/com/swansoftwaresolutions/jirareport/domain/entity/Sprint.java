package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jira_sprint_id")
    private JiraSprint jiraSprint;

    @Column(name = "not_count_target")
    private boolean notCountTarget;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report = new Report();

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="sprint", fetch = FetchType.EAGER)
    private List<SprintDeveloper> developers = new ArrayList<>();

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

    @Transient
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

    public List<SprintDeveloper> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<SprintDeveloper> developers) {
        this.developers = developers;
    }

}
