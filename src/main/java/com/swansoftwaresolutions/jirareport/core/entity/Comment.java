package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "comments")
@NamedQueries(value = {
        @NamedQuery(name = "Comment.findByReportId", query = "FROM Comment c WHERE c.reportId = :reportId"),
        @NamedQuery(name = "Comment.findById", query = "FROM Comment c WHERE c.id = :id"),
        @NamedQuery(name = "Comment.deleteAll", query = "DELETE FROM Comment c"),
        @NamedQuery(name = "Comment.deleteByReportId", query = "DELETE FROM Comment c WHERE c.reportId = :reportId"),
})
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

    @Column(name = "created_date")
    private Date createdDate;

    public Comment(Long reportId, Long sprintId, String text, String creator, Date createdDate) {
        this.reportId = reportId;
        this.sprintId = sprintId;
        this.text = text;
        this.creator = creator;
        this.createdDate = createdDate;
    }

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
