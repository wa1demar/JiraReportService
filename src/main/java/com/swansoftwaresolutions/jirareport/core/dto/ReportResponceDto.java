package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.Date;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class ReportResponceDto {

    private Long id;

    private String title;
    private String url;
    private String creator;
    private Long creatorId;
    private Long boardId;
    private Date createdDate;
    private boolean isClosed;
    private Long typeId;
    private List<JiraUserDto> admins;

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

    public boolean isClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public List<JiraUserDto> getAdmins() {
        return admins;
    }

    public void setAdmins(List<JiraUserDto> admins) {
        this.admins = admins;
    }
}
