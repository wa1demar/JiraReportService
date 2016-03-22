package com.swansoftwaresolutions.jirareport.core.dto.dashboard;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectDashboardsDto {

    List<ProjectDashboardDto> dashboards;

    public List<ProjectDashboardDto> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<ProjectDashboardDto> dashboards) {
        this.dashboards = dashboards;
    }
}
