package com.swansoftwaresolutions.jirareport.core.dto;

/**
 * @author Vitaliy Holovko
 */
public class ImportedJiraBoardDto {
    private Long id;
    private String name;
    private String type;
    private String projectKey;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportedJiraBoardDto that = (ImportedJiraBoardDto) o;

        if (!name.equals(that.name)) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return projectKey.equals(that.projectKey);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + projectKey.hashCode();
        return result;
    }
}
