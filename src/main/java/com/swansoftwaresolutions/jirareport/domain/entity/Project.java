package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "projects")
public class Project {

    private Long id;
    private String title;
    private int sortPosition;

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
}
