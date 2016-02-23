package com.swansoftwaresolutions.jirareport.rest.dto;

import com.swansoftwaresolutions.jirareport.core.entity.SprintIssue;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamActivityDataDto implements Serializable {
    public Long sprintId;
    public Date date;
    public List<SprintIssue> issues;
}
