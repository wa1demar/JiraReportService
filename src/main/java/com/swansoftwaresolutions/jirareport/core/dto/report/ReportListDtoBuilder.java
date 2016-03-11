package com.swansoftwaresolutions.jirareport.core.dto.report;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportListDtoBuilder {

    private ReportListDto listDto;

    public ReportListDtoBuilder() {
        this.listDto = new ReportListDto();
    }

    public ReportListDtoBuilder reportsDto(List<ReportDto> reportDtoList) {
        listDto.setReports(reportDtoList);
        return this;
    }

    public ReportListDto build() {
        return listDto;
    }

}
