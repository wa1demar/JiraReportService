package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class SprintIssueDto implements Serializable {
    public Long id;
    public Long sprintId;
    public Long agileSprintId;
    public String key;
    public Double point;
    public String typeName;
    public String assignee;
    public String statusName;

    public String issueDate;
    public Double hours;
}
