package com.swansoftwaresolutions.jirareport.core.dto.dashboard;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class ProjectReportDto {
    private  Long id;
    private String title;
    private String creator;
    private Long boardId;
    private String boardName;
    private Date createdDate;
    private Date updatedDate;
    private Date closedDate;
    private int typeId;
    private boolean closed;
    private List<JiraUserDto> admins= new ArrayList<>();
    private float targetPoints;
    private Long targetHours;
    private int targetQatDefectMin;
    private int targetQatDefectMax;
    private Long targetQatDefectHours;
    private int targetUatDefectMin;
    private int targetUatDefectMax;
    private Long targetUatDefectHours;
    private float actualPoints;
    private Long actualHours;
    private float actualQatDefectPoints;
    private Long actualQatDefectHours;
    private float actualUatDefectPoints;
    private Long actualUatDefectHours;

    private long sprintsCount;
    private String description;
    private String issues;

    private Chart chart;

    private boolean showUat;
    private long closedSprintCount;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
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

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public List<JiraUserDto> getAdmins() {
        return admins;
    }

    public void setAdmins(List<JiraUserDto> admins) {
        this.admins = admins;
    }

    public float getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(float targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public int getTargetQatDefectMin() {
        return targetQatDefectMin;
    }

    public void setTargetQatDefectMin(int targetQatDefectMin) {
        this.targetQatDefectMin = targetQatDefectMin;
    }

    public int getTargetQatDefectMax() {
        return targetQatDefectMax;
    }

    public void setTargetQatDefectMax(int targetQatDefectMax) {
        this.targetQatDefectMax = targetQatDefectMax;
    }

    public Long getTargetQatDefectHours() {
        return targetQatDefectHours;
    }

    public void setTargetQatDefectHours(Long targetQatDefectHours) {
        this.targetQatDefectHours = targetQatDefectHours;
    }

    public int getTargetUatDefectMin() {
        return targetUatDefectMin;
    }

    public void setTargetUatDefectMin(int targetUatDefectMin) {
        this.targetUatDefectMin = targetUatDefectMin;
    }

    public int getTargetUatDefectMax() {
        return targetUatDefectMax;
    }

    public void setTargetUatDefectMax(int targetUatDefectMax) {
        this.targetUatDefectMax = targetUatDefectMax;
    }

    public Long getTargetUatDefectHours() {
        return targetUatDefectHours;
    }

    public void setTargetUatDefectHours(Long targetUatDefectHours) {
        this.targetUatDefectHours = targetUatDefectHours;
    }

    public float getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(float actualPoints) {
        this.actualPoints = actualPoints;
    }

    public Long getActualHours() {
        return actualHours;
    }

    public void setActualHours(Long actualHours) {
        this.actualHours = actualHours;
    }

    public float getActualQatDefectPoints() {
        return actualQatDefectPoints;
    }

    public void setActualQatDefectPoints(float actualQatDefectPoints) {
        this.actualQatDefectPoints = actualQatDefectPoints;
    }

    public Long getActualQatDefectHours() {
        return actualQatDefectHours;
    }

    public void setActualQatDefectHours(Long actualQatDefectHours) {
        this.actualQatDefectHours = actualQatDefectHours;
    }

    public float getActualUatDefectPoints() {
        return actualUatDefectPoints;
    }

    public void setActualUatDefectPoints(float actualUatDefectPoints) {
        this.actualUatDefectPoints = actualUatDefectPoints;
    }

    public Long getActualUatDefectHours() {
        return actualUatDefectHours;
    }

    public void setActualUatDefectHours(Long actualUatDefectHours) {
        this.actualUatDefectHours = actualUatDefectHours;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public boolean isShowUat() {
        return showUat;
    }

    public void setShowUat(boolean showUat) {
        this.showUat = showUat;
    }

    public long getClosedSprintCount() {
        return closedSprintCount;
    }

    public void setClosedSprintCount(long closedSprintCount) {
        this.closedSprintCount = closedSprintCount;
    }

    public long getSprintsCount() {
        return sprintsCount;
    }

    public void setSprintsCount(long sprintsCount) {
        this.sprintsCount = sprintsCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }
}
