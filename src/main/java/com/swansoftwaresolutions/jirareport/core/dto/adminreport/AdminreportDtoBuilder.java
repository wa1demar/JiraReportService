package com.swansoftwaresolutions.jirareport.core.dto.adminreport;

/**
 * @author Vladimir Martynyuk
 */
public class AdminreportDtoBuilder {

    private AdminReportDto reportDto;

    public AdminreportDtoBuilder() {
        this.reportDto = new AdminReportDto();
    }

    public AdminreportDtoBuilder id(Long id) {
        reportDto.setId(id);
        return this;
    }

    public AdminreportDtoBuilder login(String login) {
        reportDto.setLogin(login);
        return this;
    }

    public AdminreportDtoBuilder fullName(String fullName) {
        reportDto.setFullName(fullName);
        return this;
    }

    public AdminReportDto build() {
        return reportDto;
    }

}
