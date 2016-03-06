package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "reports")
@NamedQueries(value = {
        @NamedQuery(name = "Report.findById", query = "FROM Report c WHERE c.id = :id"),
        @NamedQuery(name = "Report.findAllClosed", query = "FROM Report c WHERE c.isClosed = true"),
        @NamedQuery(name = "Report.findAllAutomaticOngoingReport", query = "FROM Report c WHERE (c.isClosed = 0 OR c.isClosed IS NULL) AND (c.typeId = 1 OR  c.typeId IS NULL) ORDER BY c.title ASC"),
        @NamedQuery(name = "Report.findAllManualOngoingReport", query = "FROM Report c WHERE (c.isClosed = 0 OR c.isClosed IS NULL) AND c.typeId = 2 ORDER BY c.title ASC"),
        @NamedQuery(name = "Report.findAllAutomaticClosedReport", query = "FROM Report c WHERE c.isClosed = 1 AND (c.typeId = 1 OR c.typeId IS NULL) ORDER BY c.title ASC"),
        @NamedQuery(name = "Report.findAllManualClosedReport", query = "FROM Report c WHERE (c.isClosed = 1 AND c.typeId = 2) ORDER BY c.title ASC"),
        @NamedQuery(name = "Report.Report.findAllClosedReportsByDateCloseFirst", query = "FROM Report c WHERE (c.isClosed = 1 AND c.closedDate >= :dataFrom  AND c.closedDate <= :dataTo) ORDER BY c.title ASC"),
        @NamedQuery(name = "Report.Report.findAllClosedReportsByDateClose", query = "FROM Report c WHERE (c.isClosed = 1 AND c.closedDate >= :dataFrom) ORDER BY c.closedDate DESC"),
        @NamedQuery(name = "Report.Report.findAllClosedReportsByDateCloseFrom", query = "FROM Report c WHERE (c.isClosed = 1 AND c.closedDate >= :dataFrom) ORDER BY c.closedDate DESC"),
        @NamedQuery(name = "Report.Report.findAllClosedReportsByDateCloseTo", query = "FROM Report c WHERE (c.isClosed = 1 AND c.closedDate <= :dataTo) ORDER BY c.closedDate DESC"),
//        @NamedQuery(name = "Report.findLastUpdatedReport", query = "FROM Report c WHERE c.isClosed = false ORDER BY c.updatedDate DESC LIMIT 5"),
        @NamedQuery(name = "Report.findReportBytId", query = "FROM Report c WHERE c.id = :id"),
//        @NamedQuery(name = "Report.findLastAddedReport", query = "FROM Report c ORDER BY c.createdDate DESC LIMIT 1"),
        @NamedQuery(name = "Report.deleteAll", query = "DELETE FROM Report c"),
})
public class Report  implements Serializable{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (id != null ? !id.equals(report.id) : report.id != null) return false;
        if (!title.equals(report.title)) return false;
        if (!creator.equals(report.creator)) return false;
        if (image != null ? !image.equals(report.image) : report.image != null) return false;
        if (!boardId.equals(report.boardId)) return false;
        if (!creatorId.equals(report.creatorId)) return false;
        if (createdDate != null ? !createdDate.equals(report.createdDate) : report.createdDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(report.updatedDate) : report.updatedDate != null) return false;
        if (syncDate != null ? !syncDate.equals(report.syncDate) : report.syncDate != null) return false;
        if (!isClosed.equals(report.isClosed)) return false;
        if (closedDate != null ? !closedDate.equals(report.closedDate) : report.closedDate != null) return false;
        if (!typeId.equals(report.typeId)) return false;
        if (targetPoints != null ? !targetPoints.equals(report.targetPoints) : report.targetPoints != null)
            return false;
        if (targetHours != null ? !targetHours.equals(report.targetHours) : report.targetHours != null) return false;
        if (targetQatDefectMin != null ? !targetQatDefectMin.equals(report.targetQatDefectMin) : report.targetQatDefectMin != null)
            return false;
        if (targetQatDefectMax != null ? !targetQatDefectMax.equals(report.targetQatDefectMax) : report.targetQatDefectMax != null)
            return false;
        if (targetQatDefectHours != null ? !targetQatDefectHours.equals(report.targetQatDefectHours) : report.targetQatDefectHours != null)
            return false;
        if (targetUatDefectMin != null ? !targetUatDefectMin.equals(report.targetUatDefectMin) : report.targetUatDefectMin != null)
            return false;
        if (targetUatDefectMax != null ? !targetUatDefectMax.equals(report.targetUatDefectMax) : report.targetUatDefectMax != null)
            return false;
        if (targetUatDefectHours != null ? !targetUatDefectHours.equals(report.targetUatDefectHours) : report.targetUatDefectHours != null)
            return false;
        if (actualPoints != null ? !actualPoints.equals(report.actualPoints) : report.actualPoints != null)
            return false;
        if (actualHours != null ? !actualHours.equals(report.actualHours) : report.actualHours != null) return false;
        if (actualQatDefectPoints != null ? !actualQatDefectPoints.equals(report.actualQatDefectPoints) : report.actualQatDefectPoints != null)
            return false;
        if (actualQatDefectHours != null ? !actualQatDefectHours.equals(report.actualQatDefectHours) : report.actualQatDefectHours != null)
            return false;
        if (actualUatDefectPoints != null ? !actualUatDefectPoints.equals(report.actualUatDefectPoints) : report.actualUatDefectPoints != null)
            return false;
        return !(actualUatDefectHours != null ? !actualUatDefectHours.equals(report.actualUatDefectHours) : report.actualUatDefectHours != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + boardId.hashCode();
        result = 31 * result + creatorId.hashCode();
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (syncDate != null ? syncDate.hashCode() : 0);
        result = 31 * result + isClosed.hashCode();
        result = 31 * result + (closedDate != null ? closedDate.hashCode() : 0);
        result = 31 * result + typeId.hashCode();
        result = 31 * result + (targetPoints != null ? targetPoints.hashCode() : 0);
        result = 31 * result + (targetHours != null ? targetHours.hashCode() : 0);
        result = 31 * result + (targetQatDefectMin != null ? targetQatDefectMin.hashCode() : 0);
        result = 31 * result + (targetQatDefectMax != null ? targetQatDefectMax.hashCode() : 0);
        result = 31 * result + (targetQatDefectHours != null ? targetQatDefectHours.hashCode() : 0);
        result = 31 * result + (targetUatDefectMin != null ? targetUatDefectMin.hashCode() : 0);
        result = 31 * result + (targetUatDefectMax != null ? targetUatDefectMax.hashCode() : 0);
        result = 31 * result + (targetUatDefectHours != null ? targetUatDefectHours.hashCode() : 0);
        result = 31 * result + (actualPoints != null ? actualPoints.hashCode() : 0);
        result = 31 * result + (actualHours != null ? actualHours.hashCode() : 0);
        result = 31 * result + (actualQatDefectPoints != null ? actualQatDefectPoints.hashCode() : 0);
        result = 31 * result + (actualQatDefectHours != null ? actualQatDefectHours.hashCode() : 0);
        result = 31 * result + (actualUatDefectPoints != null ? actualUatDefectPoints.hashCode() : 0);
        result = 31 * result + (actualUatDefectHours != null ? actualUatDefectHours.hashCode() : 0);
        return result;
    }
}
