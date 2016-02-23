package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class ReportDto implements Serializable {
    public Long id;
    public String title;
    public String url;
    public String image;
    public String creator;
    public Long creatorId;
    public Long boardId;
    public Date createdDate;
    public Date updatedDate;
    public Date syncDate;
    public Long isClosed;
    public Date closedDate;
    public Long typeId;
}
