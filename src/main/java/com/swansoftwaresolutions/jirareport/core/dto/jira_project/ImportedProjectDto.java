package com.swansoftwaresolutions.jirareport.core.dto.jira_project;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedProjectDto {
    private Long id;
    private String key;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
