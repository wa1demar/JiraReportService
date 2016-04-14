package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.Chart;
import com.swansoftwaresolutions.jirareport.core.dto.dashboard.ProjectDashboardDto;
import com.swansoftwaresolutions.jirareport.core.dto.dashboard.ProjectDashboardsDto;
import com.swansoftwaresolutions.jirareport.core.dto.dashboard.ProjectReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.service.DashboardService;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.entity.CacheProjectTotal;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
import com.swansoftwaresolutions.jirareport.domain.repository.CacheProjectTotalRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CacheProjectTotalRepository projectTotalRepository;

    @Autowired
    ReportService reportService;

    @Autowired
    ReportMapper reportMapper;


    @Override
    public ProjectDashboardsDto loadPortfolio() throws NoSuchEntityException {

        ProjectDashboardsDto dashboardsDto = new ProjectDashboardsDto();

        List<CacheProjectTotal> projectsTotal = projectTotalRepository.findAll();

        List<ProjectDashboardDto> dashboardDtos = new ArrayList<>();
        for (CacheProjectTotal total : projectsTotal) {
            ReportDto reportDto = reportMapper.toDto(total.getReport());

            ProjectReportDto projectReportDto = new ProjectReportDto();
            projectReportDto.setClosedSprintCount(total.getClosedSprintCount());
            projectReportDto.setShowUat(total.isShowUat());
            projectReportDto.setId(reportDto.getId());
            projectReportDto.setId(reportDto.getId());
            projectReportDto.setTitle(reportDto.getTitle());
            projectReportDto.setCreator(reportDto.getCreator());
            projectReportDto.setBoardId(reportDto.getBoardId());
            projectReportDto.setBoardName(reportDto.getBoardName());
            projectReportDto.setCreatedDate(reportDto.getCreatedDate());
            projectReportDto.setUpdatedDate(reportDto.getUpdatedDate());
            projectReportDto.setClosedDate(reportDto.getClosedDate());
            projectReportDto.setTypeId(reportDto.getTypeId());
            projectReportDto.setClosed(reportDto.isClosed());
            projectReportDto.setAdmins(reportDto.getAdmins());

            projectReportDto.setTargetPoints(total.getvTargetPoints());
            projectReportDto.setActualPoints(total.getvActualPoints());
            projectReportDto.setActualHours(total.getvActualHours());
            projectReportDto.setTargetHours(total.getvTargetHours());
            projectReportDto.setTargetQatDefectMin(total.getQtargetMin());
            projectReportDto.setTargetQatDefectMax(total.getQtargetMax());
            projectReportDto.setActualQatDefectPoints(total.getqActualPoints());
            projectReportDto.setTargetQatDefectHours(total.getqTargetHours());
            projectReportDto.setActualQatDefectHours(total.getqActualHours());
            projectReportDto.setTargetUatDefectMin(total.getUtargetMin());
            projectReportDto.setTargetUatDefectMax(total.getUtargetMax());
            projectReportDto.setActualUatDefectPoints(total.getuActualPoints());
            projectReportDto.setTargetUatDefectHours(total.getuTargetHours());
            projectReportDto.setActualUatDefectHours(total.getuActualHours());
            projectReportDto.setSprintsCount(total.getSprintsCount());
            projectReportDto.setChart(new Chart(
                    total.getChartLabels().split(","),
                    Arrays.stream(total.getChartTarget().substring(1, total.getChartTarget().length()-1).split(",")).map(String::trim).mapToDouble(Double::parseDouble).toArray(),
                    Arrays.stream(total.getChartActual().substring(1, total.getChartActual().length()-1).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray()
            ));


            ProjectDashboardDto dashboardDto = new ProjectDashboardDto();
            dashboardDto.setReport(projectReportDto);

            dashboardDtos.add(dashboardDto);
        }

        dashboardsDto.setDashboards(dashboardDtos);

        return dashboardsDto;
    }

    @Override
    public ProjectDashboardsDto loadPortfolioPaged(int page) {
        ProjectDashboardsDto dashboardsDto = new ProjectDashboardsDto();

        Paged paged = projectTotalRepository.findAllPaged(page);
        List<CacheProjectTotal> projectsTotal = paged.getList();

        List<ProjectDashboardDto> dashboardDtos = new ArrayList<>();
        for (CacheProjectTotal total : projectsTotal) {
            ReportDto reportDto = reportMapper.toDto(total.getReport());

            ProjectReportDto projectReportDto = new ProjectReportDto();
            projectReportDto.setClosedSprintCount(total.getClosedSprintCount());
            projectReportDto.setShowUat(total.isShowUat());
            projectReportDto.setId(reportDto.getId());
            projectReportDto.setId(reportDto.getId());
            projectReportDto.setTitle(reportDto.getTitle());
            projectReportDto.setCreator(reportDto.getCreator());
            projectReportDto.setBoardId(reportDto.getBoardId());
            projectReportDto.setBoardName(reportDto.getBoardName());
            projectReportDto.setCreatedDate(reportDto.getCreatedDate());
            projectReportDto.setUpdatedDate(reportDto.getUpdatedDate());
            projectReportDto.setClosedDate(reportDto.getClosedDate());
            projectReportDto.setTypeId(reportDto.getTypeId());
            projectReportDto.setClosed(reportDto.isClosed());
            projectReportDto.setAdmins(reportDto.getAdmins());

            projectReportDto.setTargetPoints(total.getvTargetPoints());
            projectReportDto.setActualPoints(total.getvActualPoints());
            projectReportDto.setActualHours(total.getvActualHours());
            projectReportDto.setTargetHours(total.getvTargetHours());
            projectReportDto.setTargetQatDefectMin(total.getQtargetMin());
            projectReportDto.setTargetQatDefectMax(total.getQtargetMax());
            projectReportDto.setActualQatDefectPoints(total.getqActualPoints());
            projectReportDto.setTargetQatDefectHours(total.getqTargetHours());
            projectReportDto.setActualQatDefectHours(total.getqActualHours());
            projectReportDto.setTargetUatDefectMin(total.getUtargetMin());
            projectReportDto.setTargetUatDefectMax(total.getUtargetMax());
            projectReportDto.setActualUatDefectPoints(total.getuActualPoints());
            projectReportDto.setTargetUatDefectHours(total.getuTargetHours());
            projectReportDto.setActualUatDefectHours(total.getuActualHours());
            projectReportDto.setSprintsCount(total.getSprintsCount());
            projectReportDto.setChart(new Chart(
                    total.getChartLabels().split(","),
                    Arrays.stream(total.getChartTarget().substring(1, total.getChartTarget().length()-1).split(",")).map(String::trim).mapToDouble(Double::parseDouble).toArray(),
                    Arrays.stream(total.getChartActual().substring(1, total.getChartActual().length()-1).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray()
            ));


            ProjectDashboardDto dashboardDto = new ProjectDashboardDto();
            dashboardDto.setReport(projectReportDto);

            dashboardDtos.add(dashboardDto);
        }

        dashboardsDto.setDashboards(dashboardDtos);
        dashboardsDto.setTotalPages((int)Math.floor(paged.getTotal() / 10) + 1);
        dashboardsDto.setPage(page);
        dashboardsDto.setTotalItems(paged.getTotal());

        return dashboardsDto;
    }
}
