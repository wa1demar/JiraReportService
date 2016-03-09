package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vitaliy Holovko
 */
@Entity
@Table(name = "jira_boards")
public class JiraBoard {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "project_key")
    private String projectKey;

    @Column(name = "project_jira_id")
    private Long projectJiraId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public Long getProjectJiraId() {
        return projectJiraId;
    }

    public void setProjectJiraId(Long projectJiraId) {
        this.projectJiraId = projectJiraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraBoard jiraBoard = (JiraBoard) o;

        if (!boardId.equals(jiraBoard.boardId)) return false;
        if (!name.equals(jiraBoard.name)) return false;
        return type.equals(jiraBoard.type);

    }

    @Override
    public int hashCode() {
        int result = boardId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
