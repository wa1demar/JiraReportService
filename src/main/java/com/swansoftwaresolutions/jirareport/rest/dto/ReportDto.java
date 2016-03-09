package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportDto implements Serializable {
    private Long id;

    private String title;
    private String url;
    private String image;
    private String creator;
    private Long creatorId;
    private Long boardId;
    private Date createdDate;
    private Date updatedDate;
    private Date syncDate;
    private boolean isClosed;
    private Date closedDate;
    private Long typeId;
    private List<AdminReportDto> adminReports;
    private Long targetPoints;
    private Long targetHours;
    private Long targetQatDefectMin;
    private Long targetQatDefectMax;
    private Long targetQatDefectHours;
    private Long targetUatDefectMin;
    private Long targetUatDefectMax;
    private Long targetUatDefectHours;
    private Long actualPoints;
    private Double actualHours;
    private Long actualQatDefectPoints;
    private Double actualQatDefectHours;
    private Long actualUatDefectPoints;
    private Double actualUatDefectHours;
    private List<AdminReportDto> admins;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public List<AdminReportDto> getAdminReports() {
        return adminReports;
    }

    public void setAdminReports(List<AdminReportDto> adminReports) {
        this.adminReports = adminReports;
    }

    public Long getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(Long targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public Long getTargetQatDefectMin() {
        return targetQatDefectMin;
    }

    public void setTargetQatDefectMin(Long targetQatDefectMin) {
        this.targetQatDefectMin = targetQatDefectMin;
    }

    public Long getTargetQatDefectMax() {
        return targetQatDefectMax;
    }

    public void setTargetQatDefectMax(Long targetQatDefectMax) {
        this.targetQatDefectMax = targetQatDefectMax;
    }

    public Long getTargetQatDefectHours() {
        return targetQatDefectHours;
    }

    public void setTargetQatDefectHours(Long targetQatDefectHours) {
        this.targetQatDefectHours = targetQatDefectHours;
    }

    public Long getTargetUatDefectMin() {
        return targetUatDefectMin;
    }

    public void setTargetUatDefectMin(Long targetUatDefectMin) {
        this.targetUatDefectMin = targetUatDefectMin;
    }

    public Long getTargetUatDefectMax() {
        return targetUatDefectMax;
    }

    public void setTargetUatDefectMax(Long targetUatDefectMax) {
        this.targetUatDefectMax = targetUatDefectMax;
    }

    public Long getTargetUatDefectHours() {
        return targetUatDefectHours;
    }

    public void setTargetUatDefectHours(Long targetUatDefectHours) {
        this.targetUatDefectHours = targetUatDefectHours;
    }

    public Long getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(Long actualPoints) {
        this.actualPoints = actualPoints;
    }

    public Double getActualHours() {
        return actualHours;
    }

    public void setActualHours(Double actualHours) {
        this.actualHours = actualHours;
    }

    public Long getActualQatDefectPoints() {
        return actualQatDefectPoints;
    }

    public void setActualQatDefectPoints(Long actualQatDefectPoints) {
        this.actualQatDefectPoints = actualQatDefectPoints;
    }

    public Double getActualQatDefectHours() {
        return actualQatDefectHours;
    }

    public void setActualQatDefectHours(Double actualQatDefectHours) {
        this.actualQatDefectHours = actualQatDefectHours;
    }

    public Long getActualUatDefectPoints() {
        return actualUatDefectPoints;
    }

    public void setActualUatDefectPoints(Long actualUatDefectPoints) {
        this.actualUatDefectPoints = actualUatDefectPoints;
    }

    public Double getActualUatDefectHours() {
        return actualUatDefectHours;
    }

    public void setActualUatDefectHours(Double actualUatDefectHours) {
        this.actualUatDefectHours = actualUatDefectHours;
    }

    public List<AdminReportDto> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminReportDto> admins) {
        this.admins = admins;
    }
}
