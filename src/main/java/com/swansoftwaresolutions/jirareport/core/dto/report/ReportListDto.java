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
}
