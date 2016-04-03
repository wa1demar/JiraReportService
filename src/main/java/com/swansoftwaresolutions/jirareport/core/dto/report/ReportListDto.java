package com.swansoftwaresolutions.jirareport.core.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportListDto implements Serializable {

    private int page;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage = 10;
    private List<ReportDto> reports = new ArrayList<>();

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ReportDto> getReports() {
        return reports;
    }

    public void setReports(List<ReportDto> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportListDto that = (ReportListDto) o;

        return reports.equals(that.reports);

    }

    @Override
    public int hashCode() {
        return reports.hashCode();
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}
