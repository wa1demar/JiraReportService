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
    public boolean isClosed;
    public Date closedDate;
    public Long typeId;
    public Long targetPoints;
    public Long targetHours;
    public Long targetQatDefectMin;
    public Long targetQatDefectMax;
    public Long targetQatDefectHours;
    public Long targetUatDefectMin;
    public Long targetUatDefectMax;
    public Long targetUatDefectHours;
    public Long actualPoints;
    public Double actualHours;
    public Long actualQatDefectPoints;
    public Double actualQatDefectHours;
    public Long actualUatDefectPoints;
    public Double actualUatDefectHours;
}
