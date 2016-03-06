package com.swansoftwaresolutions.jirareport.rest.dto;

/**
 * @author Vitaliy Holovko
 */

public class JiraBoardInfoDto {

    private Long id;
    private Long boardId;
    private String name;
    private String type;
    private String projectKey;
    private Long projectJiraId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraBoardInfoDto jiraBoard = (JiraBoardInfoDto) o;

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
