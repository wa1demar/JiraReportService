package com.swansoftwaresolutions.jirareport.domain.entity.builder;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportBuilder {

    private Report report;

    public ReportBuilder() {
        this.report = new Report();
    }

    public ReportBuilder id(long id) {
        this.report.setId(id);
        return this;
    }

    public ReportBuilder title(String title) {
        this.report.setTitle(title);
        return this;
    }

    public ReportBuilder creator(String creator) {
        this.report.setCreator(creator);
        return this;
    }

    public ReportBuilder image(String image) {
        this.report.setImage(image);
        return this;
    }

    public ReportBuilder boardId(Long boardId) {
        this.report.setBoardId(boardId);
        return this;
    }

    public ReportBuilder createdDate(Date createdDate) {
        this.report.setCreatedDate(createdDate);
        return this;
    }

    public ReportBuilder updatedDate(Date updatedDate) {
        this.report.setUpdatedDate(updatedDate);
        return this;
    }

    public ReportBuilder syncDate(Date syncDate) {
        this.report.setSyncDate(syncDate);
        return this;
    }

    public ReportBuilder isClosed(boolean isClosed) {
        this.report.setClosed(isClosed);
        return this;
    }

    public ReportBuilder closedDate(Date closedDate) {
        this.report.setClosedDate(closedDate);
        return this;
    }

    public ReportBuilder typeId(int typeId) {
        this.report.setTypeId(typeId);
        return this;
    }

    public ReportBuilder admins(List<JiraUser> admins) {
        this.report.setAdmins(admins);
        return this;
    }

    public Report build() {
        return report;
    }

}
