package com.swansoftwaresolutions.jirareport.core.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class NewReportDto implements Serializable {
    private String title;
    private String creator;
    private Long boardId;
    private Integer typeId;
    private String[] admins;
    private boolean closed;
    private Date closedDate;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String[] getAdmins() {
        return admins;
    }

    public void setAdmins(String[] admins) {
        this.admins = admins;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }
}
