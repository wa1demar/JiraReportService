package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamsDto {

    private List<SprintTeamDto> teams;

    public SprintTeamsDto(List<SprintTeamDto> teams) {
        this.teams = teams;
    }

    public List<SprintTeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<SprintTeamDto> teams) {
        this.teams = teams;
    }
}
