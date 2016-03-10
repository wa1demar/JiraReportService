package com.swansoftwaresolutions.jirareport.core.dto.adminreport;

/**
 * @author Vitaliy Holovko
 */

public class AdminReportDto {

    private Long id;
    private String login;
    private String fullName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
