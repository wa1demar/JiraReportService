package com.swansoftwaresolutions.jirareport.core.dto.report;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class NewReportDto implements Serializable {
    private String title;
    private String creator;
    private Long boardId;
    private int typeId;
    private String[] admins;

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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String[] getAdmins() {
        return admins;
    }

    public void setAdmins(String[] admins) {
        this.admins = admins;
    }
}
