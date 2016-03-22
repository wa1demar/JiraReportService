package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.ProjectDashboardsDto;
import com.swansoftwaresolutions.jirareport.core.service.DashboardService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class PortfolioController {

    @Autowired
    DashboardService dashboardService;

    @RequestMapping(value = "/rest/v1/portfolio", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ProjectDashboardsDto> getAllUsers() throws NoSuchEntityException {

        ProjectDashboardsDto dashboards = dashboardService.loadPortfolio();

        return new ResponseEntity<>(dashboards, HttpStatus.OK);
    }
}
