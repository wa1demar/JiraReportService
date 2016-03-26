package com.swansoftwaresolutions.jirareport.domain.entity;

import com.swansoftwaresolutions.jirareport.domain.enums.TaskStatus;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "label")
    private String label;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "position")
    private int order;

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
