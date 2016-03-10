package com.swansoftwaresolutions.jirareport.core.dto.report;

import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminReportDto;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportDtoBuilder {

    private ReportDto dto;

    public ReportDtoBuilder() {
        this.dto = new ReportDto();
    }

    public ReportDtoBuilder id(Long id) {
        dto.setId(id);
        return this;
    }

    public ReportDtoBuilder title(String title) {
        dto.setTitle(title);
        return this;
    }

    public ReportDtoBuilder creator(String creator) {
        dto.setCreator(creator);
        return this;
    }

    public ReportDtoBuilder creatorId(Long creatorId) {
        dto.setCreatorId(creatorId);
        return this;
    }

    public ReportDtoBuilder boardId(Long boardId) {
        dto.setBoardId(boardId);
        return this;
    }

    public ReportDtoBuilder boardName(String boardName) {
        dto.setBoardName(boardName);
        return this;
    }

    public ReportDtoBuilder createdDate(Date createdDate) {
        dto.setCreatedDate(createdDate);
        return this;
    }

    public ReportDtoBuilder updatedDate(Date updatedDate) {
        dto.setUpdatedDate(updatedDate);
        return this;
    }

    public ReportDtoBuilder closedDate(Date closedDate) {
        dto.setClosedDate(closedDate);
        return this;
    }

    public ReportDtoBuilder typeId(long typeId) {
        dto.setTypeId(typeId);
        return this;
    }

    public ReportDtoBuilder closed(boolean closed) {
        dto.setClosed(closed);
        return this;
    }

    public ReportDtoBuilder admins(List<AdminReportDto> admins) {
        dto.setAdmins(admins);
        return this;
    }

    public ReportDto build() {
        return dto;
    }

}
