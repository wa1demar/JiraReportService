package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "sprint_id")
    private Long sprintId;

    @Column(name = "text")
    private String text;

    @Column(name = "creator")
    private String creator;

    @Column(name = "creator_name")
    private String creatorDisplayName;

    @Column(name = "created_date")
    private Date createdDate;

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

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
