package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.projects.ExportProjectsDtos;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectFilterData;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.web.view.ExcelProjectReportView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vladimir Martynyuk
 */
@Controller
public class ExportProjectController extends AbstractController {

    @Autowired
    ProjectService projectService;

    @Override
    @RequestMapping(value = "/rest/v1/projects/export")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExportProjectsDtos allProjects = projectService.findAllForExport(new ProjectFilterData());

        response.setHeader("Content-Disposition", "inline; filename=Project Assignments.xls");
        // Make sure to set the correct content type
        response.setContentType("application/vnd.ms-excel");

        return new ModelAndView(new ExcelProjectReportView(), "projectData", allProjects);

    }
}
