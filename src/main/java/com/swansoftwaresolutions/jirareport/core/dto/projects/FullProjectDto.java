package com.swansoftwaresolutions.jirareport.core.dto.projects;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullProjectUserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullProjectDto {

    private Long id;
    private String title;
    private int sortPosition;
    private int allMembersCount;
    private int membersCount;
    private List<FullProjectUserDto> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public List<FullProjectUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<FullProjectUserDto> users) {
        this.users = users;
    }

    public int getAllMembersCount() {
        return allMembersCount;
    }

    public void setAllMembersCount(int allMembersCount) {
        this.allMembersCount = allMembersCount;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }
}
