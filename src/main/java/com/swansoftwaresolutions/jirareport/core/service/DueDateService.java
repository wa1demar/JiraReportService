package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDatesDto;

/**
 * @author Vladimir Martynyuk
 */
public interface DueDateService {
    DueDatesDto retrieveDueDates(int page);
}
