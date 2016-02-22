package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "reports")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "creator")
    private String creator;

    @Column(name = "image")
    private String image;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "sync_date")
    private Date syncDate;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "closed_date")
    private Date closedDate;

    @Column(name = "type_ID")
    private Long typeId;

    @Column(name = "target_points")
    private Long targetPoints;

    @Column(name = "target_hours")
    private Long targetHours;

    @Column(name = "target_qat_defect_min")
    private Long targetQatDefectMin;

    @Column(name = "target_qat_defect_max")
    private Long targetQatDefectMax;

    @Column(name = "target_qat_defect_hours")
    private Long targetQatDefectHours;

    @Column(name = "target_uat_defect_min")
    private Long targetUatDefectMin;

    @Column(name = "target_uat_defect_max")
    private Long targetUatDefectMax;

    @Column(name = "target_uat_defect_hours")
    private Long targetUatDefectHours;

    @Column(name = "actual_points")
    private Long actualPoints;

    @Column(name = "actual_hours")
    private Double actualHours;

    @Column(name = "actual_qat_defect_points")
    private Long actualQatDefectPoints;

    @Column(name = "actual_qat_defect_hours")
    private Double actualQatDefectHours;

    @Column(name = "actual_uat_defect_points")
    private Long actualUatDefectPoints;

    @Column(name = "actual_uat_defect_hours")
    private Double actualUatDefectHours;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
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
}