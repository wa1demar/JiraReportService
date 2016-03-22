package com.swansoftwaresolutions.jirareport.core.dto.dashboard;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class ProjectDashboardDto {

    private ProjectReportDto report;
    private List<SprintProjectReportDto> sprints;

    public ProjectReportDto getReport() {
        return report;
    }

    public void setReport(ProjectReportDto report) {
        this.report = report;
    }

    public List<SprintProjectReportDto> getSprints() {
        return sprints;
    }

    public void setSprints(List<SprintProjectReportDto> sprints) {
        this.sprints = sprints;
    }
}
