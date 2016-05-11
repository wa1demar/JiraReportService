package com.swansoftwaresolutions.jirareport.core.dto.projects;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectDto {
    private Long id;
    private String title;
    private int sortPosition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }
}
