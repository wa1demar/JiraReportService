package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name = "sprints")
@NamedQueries(value = {
        @NamedQuery(name = "Sprint.findByReportId", query = "FROM Sprint c WHERE c.reportId = :reportId"),
        @NamedQuery(name = "Sprint.findById", query = "FROM Sprint c WHERE c.id = :id"),
        @NamedQuery(name = "Sprint.findByReportIdAndAgileSprintId", query = "FROM Sprint c WHERE c.reportId = :reportId AND c.agileSprintId = :agileSprintId"),
        @NamedQuery(name = "Sprint.findByAgileSprintId", query = "FROM Sprint c WHERE c.agileSprintId = :agileSprintId"),
        @NamedQuery(name = "Sprint.deleteAll", query = "DELETE FROM Sprint c"),
        @NamedQuery(name = "Sprint.deleteByReportId", query = "DELETE FROM Sprint c WHERE c.reportId = :reportId"),
})
public class Sprint implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "agile_sprint_id")
    private Long agileSprintId;

    @Column(name = "not_count_target")
    private Long notCountTarget;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "type")
    private Long type;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "complete_date")
    private Date completeDate;

    @Column(name = "show_uat")
    private Long showUat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getAgileSprintId() {
        return agileSprintId;
    }

    public void setAgileSprintId(Long agileSprintId) {
        this.agileSprintId = agileSprintId;
    }

    public Long getNotCountTarget() {
        return notCountTarget;
    }

    public void setNotCountTarget(Long notCountTarget) {
        this.notCountTarget = notCountTarget;
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    public Long getShowUat() {
        return showUat;
    }

    public void setShowUat(Long showUat) {
        this.showUat = showUat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprint sprint = (Sprint) o;

        if (!agileSprintId.equals(sprint.agileSprintId)) return false;
        if (!name.equals(sprint.name)) return false;
        if (state != null ? !state.equals(sprint.state) : sprint.state != null) return false;
        return type.equals(sprint.type);

    }

    @Override
    public int hashCode() {
        int result = agileSprintId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + type.hashCode();
        return result;
    }
}
