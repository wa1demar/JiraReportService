package com.swansoftwaresolutions.jirareport.core.dto.jira_issue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class DueDatesDto {

    List<DueDateDto> dates = new ArrayList<>();

    public List<DueDateDto> getDates() {
        return dates;
    }

    public void setDates(List<DueDateDto> dates) {
        this.dates = dates;
    }
}
