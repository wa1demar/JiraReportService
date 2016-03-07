package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.rest.dto.InfoForNewReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.ReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.responce.ResponceReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@RestController
public class ReportController {

    private JiraUserService jiraUserService;
    private ReportService reportService;
    private JiraBoardService jiraBoardService;

    @Autowired
    public ReportController(JiraUserService jiraUserService, ReportService reportService, JiraBoardService jiraBoardService) {
        this.jiraUserService = jiraUserService;
        this.reportService = reportService;
        this.jiraBoardService = jiraBoardService;
    }

    @RequestMapping(value = "/rest/report/datainfo", method = RequestMethod.GET)
    private ResponseEntity<InfoForNewReportDto> listResponseEntity() {
        return new ResponseEntity<>(prepareListsOfProjectsAndUsers(), HttpStatus.OK);
    }

    private InfoForNewReportDto prepareListsOfProjectsAndUsers() {
        InfoForNewReportDto infoForNewReport = new InfoForNewReportDto();
        infoForNewReport.boards = jiraBoardService.findAllBoardForInfo();
        infoForNewReport.users = jiraUserService.findAll();

        return infoForNewReport;
    }

    @RequestMapping(value = "/rest/auth/create", method = RequestMethod.POST)
    private ResponseEntity<NewReportDto> addNewReport(@Valid @RequestBody NewReportDto newReportDto) throws NoSuchEntityException {
        NewReportDto reportDto = reportService.save(newReportDto);

        if (reportDto != null) {
            return new ResponseEntity<>(reportDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(reportDto, HttpStatus.NO_CONTENT);
        }

    }

    @RequestMapping(value = "/rest/report/delete", method = RequestMethod.DELETE)
    private ResponseEntity<ResponceReportDto> deleteReportById(@RequestParam(value = "id") long id) {
        ResponceReportDto responsePostDto;
        HttpStatus httpStatus;

        try {
            if (reportService.findById(id) != null) {
                reportService.deleteById(id);
                responsePostDto = new ResponceReportDto(true, "Report deleted successfully.");
                httpStatus = HttpStatus.OK;
            } else {
                responsePostDto = new ResponceReportDto(false, "Can't found Report.");
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            responsePostDto = new ResponceReportDto(false, "Can't found Report.");
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responsePostDto, httpStatus);
    }

    @RequestMapping(value = "/rest/report/allreports", method = RequestMethod.GET)
    private ResponseEntity<List<ReportDto>> findAllReports() {
        //ToDo Add responce. Need to modify Dto
        List<ReportDto> reportDtos = reportService.findAll();

        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/auth/copy", method = RequestMethod.POST)
    private ResponseEntity<ResponceReportDto> makeReportCopyById(@RequestParam(value = "id") long id, @RequestParam(value = "name") String reportName) {
        ResponceReportDto responsePostDto;
        HttpStatus httpStatus;

        try {
            NewReportDto newReportDto = reportService.findNewReportById(id);
            newReportDto.setTitle(reportName);
            newReportDto.setId(null);
            if (reportService.save(newReportDto) != null) {
                responsePostDto = new ResponceReportDto(true, "Report added successfully.");
                httpStatus = HttpStatus.OK;
            } else {
                responsePostDto = new ResponceReportDto(false, "Can't added Report.");
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            responsePostDto = new ResponceReportDto(false, "Can't added Report.");
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responsePostDto, httpStatus);
    }


}