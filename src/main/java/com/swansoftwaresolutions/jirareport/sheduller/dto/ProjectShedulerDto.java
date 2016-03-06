package com.swansoftwaresolutions.jirareport.sheduller.dto;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectShedulerDto {

    private int id;
    private String key;
    private String name;
    private Long jiraId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getJiraId() {
        return jiraId;
    }

    public void setJiraId(Long jiraId) {
        this.jiraId = jiraId;
    }
}
