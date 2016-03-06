package com.swansoftwaresolutions.jirareport.rest.dto;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public class JiraUserDto {
    private Long id;
    private String fullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
