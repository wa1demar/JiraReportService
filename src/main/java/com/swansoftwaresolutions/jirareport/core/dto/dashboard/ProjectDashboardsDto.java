package com.swansoftwaresolutions.jirareport.core.dto.dashboard;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectDashboardsDto {

    private int page;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage = 10;
    List<ProjectDashboardDto> dashboards;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<ProjectDashboardDto> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<ProjectDashboardDto> dashboards) {
        this.dashboards = dashboards;
    }
}
