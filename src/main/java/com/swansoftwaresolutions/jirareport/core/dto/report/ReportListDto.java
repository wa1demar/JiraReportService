package com.swansoftwaresolutions.jirareport.core.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportListDto implements Serializable {
    private List<ReportDto> reports = new ArrayList<>();

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
}
