package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDto implements Serializable {
    public Long id;
    public Long reportId;
    public Long agileSprintId;
    public Long notCountTarget;
    public String name;
    public String state;
    public Long type;
    public Date startDate;
    public Date endDate;
    public Date completeDate;
    public Long showUat;
}
