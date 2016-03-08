package com.swansoftwaresolutions.jirareport.rest.dto;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vitaliy Holovko
 */

public class AdminReportDto {

    private Long id;
    private Long reportId;
    private String name;
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
