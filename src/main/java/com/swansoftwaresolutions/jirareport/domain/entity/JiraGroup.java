package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name = "jira_groups")
public class JiraGroup {

    @Id
    @Column(name = "name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
