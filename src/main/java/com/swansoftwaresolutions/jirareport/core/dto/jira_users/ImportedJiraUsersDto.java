package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraUsersDto {
    private List<ImportedJiraUserDto> values;

    public List<ImportedJiraUserDto> getValues() {
        return values;
    }

    public void setValues(List<ImportedJiraUserDto> values) {
        this.values = values;
    }
}
