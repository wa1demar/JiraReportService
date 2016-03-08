package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name = "admin_reports")
public class AdminReport implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
