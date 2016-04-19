package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "sprints")
public class Sprint  implements Serializable {
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

    @Column(name = "show_out_of_range")
    private boolean showOutOfRange;

    @Column(name = "type")
    private int type;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
    @JoinColumn(name = "jira_sprint_id")
    private JiraSprint jiraSprint;

    @Column(name = "not_count_target")
    private boolean notCountTarget;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report = new Report();

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="sprint", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
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

    public boolean isShowOutOfRange() {
        return showOutOfRange;
    }

    public void setShowOutOfRange(boolean showOutOfRange) {
        this.showOutOfRange = showOutOfRange;
    }
}
