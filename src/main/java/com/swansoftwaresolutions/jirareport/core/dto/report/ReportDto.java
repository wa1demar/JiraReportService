package com.swansoftwaresolutions.jirareport.core.dto.report;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportDto implements Serializable {
    private Long id;
    private String title;
    private String creator;
//    private Long creatorId;
    private Long boardId;
    private Long jiraBoardId;
    private String boardName;
    private Date createdDate;
    private Date updatedDate;
    private Date closedDate;
    private int typeId;
    private boolean closed;
    private List<JiraUserDto> admins= new ArrayList<>();

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

//    public Long getCreatorId() {
//        return creatorId;
//    }
//
//    public void setCreatorId(Long creatorId) {
//        this.creatorId = creatorId;
//    }

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ReportDto reportDto = (ReportDto) o;
//
//        if (typeId != reportDto.typeId) return false;
//        if (closed != reportDto.closed) return false;
//        if (!id.equals(reportDto.id)) return false;
//        if (!title.equals(reportDto.title)) return false;
//        if (!creator.equals(reportDto.creator)) return false;
//        return boardId.equals(reportDto.boardId);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id.hashCode();
//        result = 31 * result + title.hashCode();
//        result = 31 * result + creator.hashCode();
//        result = 31 * result + boardId.hashCode();
//        result = 31 * result + typeId;
//        result = 31 * result + (closed ? 1 : 0);
//        return result;
//    }

    public Long getJiraBoardId() {
        return jiraBoardId;
    }

    public void setJiraBoardId(Long jiraBoardId) {
        this.jiraBoardId = jiraBoardId;
    }
}
