package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Entity
@Table(name = "admin_reports")
public class AdminReport implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinTable(name="report_adminreport",
            joinColumns={@JoinColumn(name="report_id")},
            inverseJoinColumns={@JoinColumn(name="adminreport_id")})
private Report report;

    @Column(name = "name")
    private String login;

    @Column(name = "display_name")
    private String fullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
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
