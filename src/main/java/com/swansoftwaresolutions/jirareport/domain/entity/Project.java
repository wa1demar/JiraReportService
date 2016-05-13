package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "projects")
public class Project {

    private Long id;
    private String title;
    private int sortPosition;
    private List<JiraUser> users = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "sort_position")
    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    public List<JiraUser> getUsers() {
        return users;
    }

    public void setUsers(List<JiraUser> users) {
        this.users = users;
    }
}
