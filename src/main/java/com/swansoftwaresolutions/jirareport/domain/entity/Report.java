package com.swansoftwaresolutions.jirareport.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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

//    @Column(name = "creator_id")
//    private Long creatorId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "sync_date")
    private Date syncDate;

    @Column(name = "is_closed")
    private boolean isClosed;

    @Column(name = "closed_date")
    private Date closedDate;

    @Column(name = "type_ID")
    private Integer typeId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "admins_reports", joinColumns = {
            @JoinColumn(name = "report_id") },
            inverseJoinColumns = { @JoinColumn(name = "admin_login") })
    private List<JiraUser> admins = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report")
    private Set<Sprint> sprints = new HashSet<>();


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

//    public Long getCreatorId() {
//        return creatorId;
//    }
//
//    public void setCreatorId(Long creatorId) {
//        this.creatorId = creatorId;
//    }

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

    public boolean getClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<JiraUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<JiraUser> admins) {
        this.admins = admins;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (id != null ? !id.equals(report.id) : report.id != null) return false;
        if (!title.equals(report.title)) return false;
        if (!creator.equals(report.creator)) return false;
        return boardId.equals(report.boardId);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + boardId.hashCode();
        return result;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }
}
