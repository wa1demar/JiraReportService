package com.swansoftwaresolutions.jirareport.core.dto.task;

import com.swansoftwaresolutions.jirareport.domain.enums.TaskStatus;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class TaskDto {

    private String name;
    private TaskStatus status;
    private String label;
    private Date lastUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
