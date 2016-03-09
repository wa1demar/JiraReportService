package com.swansoftwaresolutions.jirareport.core.dto;

/**
 * @author Vitaliy Holovko
 */

public class JiraBoardDto {

    private Long id;
    private Long boardId;
    private String name;
    private String type;
    private String projectKey;
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

        JiraBoardDto jiraBoard = (JiraBoardDto) o;

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
