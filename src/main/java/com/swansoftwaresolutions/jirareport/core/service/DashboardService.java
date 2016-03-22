package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.ProjectDashboardsDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface DashboardService {
    ProjectDashboardsDto loadPortfolio() throws NoSuchEntityException;
}
