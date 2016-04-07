package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "due_dates")
public class DueDate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "issue_key")
    private String issueKey;

    @Column(name = "description")
    private String description;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="issue_id")
    private JiraIssue issue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public JiraIssue getIssue() {
        return issue;
    }

    public void setIssue(JiraIssue issue) {
        this.issue = issue;
    }
}
