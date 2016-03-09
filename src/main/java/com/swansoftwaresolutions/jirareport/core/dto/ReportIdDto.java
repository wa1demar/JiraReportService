package com.swansoftwaresolutions.jirareport.core.dto;

/**
 * @author Vladimir Martynyuk
 */
public class ReportIdDto {
    private long reportId;
    private int typeId;

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
