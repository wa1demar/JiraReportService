package com.swansoftwaresolutions.jirareport.core.dto.report;

/**
 * @author Vladimir Martynyuk
 */
public class NewReportDtoBuilder {

    private NewReportDto dto;

    public NewReportDtoBuilder() {
        this.dto = new NewReportDto();
    }

    public NewReportDtoBuilder title(String title) {
        dto.setTitle(title);
        return this;
    }

    public NewReportDtoBuilder creator(String creator) {
        dto.setCreator(creator);
        return this;
    }

    public NewReportDtoBuilder boardId(Long boardId) {
        dto.setBoardId(boardId);
        return this;
    }

    public NewReportDtoBuilder typeId(int typeId) {
        dto.setTypeId(typeId);
        return this;
    }

    public NewReportDtoBuilder admins(String[] admins) {
        dto.setAdmins(admins);
        return this;
    }

    public NewReportDto build() {
        return dto;
    }

}
