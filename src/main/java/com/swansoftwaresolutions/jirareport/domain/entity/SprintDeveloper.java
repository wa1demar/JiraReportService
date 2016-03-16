package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "sprint_developers")
public class SprintDeveloper implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jira_user_login")
    private JiraUser jiraUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="sprint_id")
    private Sprint sprint;

    @Column(name = "engineer_level")
    private Long engineerLevel;

    @Column(name = "participation_level")
    private Double participationLevel;

    @Column(name = "days_in_sprint")
    private int daysInSprint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JiraUser getJiraUser() {
        return jiraUser;
    }

    public void setJiraUser(JiraUser jiraUser) {
        this.jiraUser = jiraUser;
    }

    public Long getEngineerLevel() {
        return engineerLevel;
    }

    public void setEngineerLevel(Long engineerLevel) {
        this.engineerLevel = engineerLevel;
    }

    public Double getParticipationLevel() {
        return participationLevel;
    }

    public void setParticipationLevel(Double participationLevel) {
        this.participationLevel = participationLevel;
    }

    public int getDaysInSprint() {
        return daysInSprint;
    }

    public void setDaysInSprint(int daysInSprint) {
        this.daysInSprint = daysInSprint;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }
}
