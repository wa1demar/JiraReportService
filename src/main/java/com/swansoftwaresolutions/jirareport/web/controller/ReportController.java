package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.responce.ResponceReportDto;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;
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
@RequestMapping("/rest")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @RequestMapping(value = "/v1/report", method = RequestMethod.POST)
    @RequestMapping(value = "/auth/create", method = RequestMethod.POST)
    private ResponseEntity<ReportResponceDto> addNewReport(@Valid @RequestBody ReportDto reportNew) throws NoSuchEntityException {
        ReportResponceDto reportDto = reportService.save(reportNew);
        HttpStatus httpStatus;

        if (reportDto != null) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(reportDto, httpStatus);
    }

    @RequestMapping(value = "/v1/report", method = RequestMethod.DELETE)
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

    @RequestMapping(value = "/auth/report", method = RequestMethod.GET)
//    @RequestMapping(value = "/v1/report", method = RequestMethod.GET)
    private ResponseEntity<List<ReportDto>> findAllReports() {
        //ToDo Add responce. Need to modify Dto
        List<ReportDto> reportDtos = reportService.findAll();

        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/report/copy", method = RequestMethod.POST)
    private ResponseEntity<ResponceReportDto> makeReportCopyById(@RequestParam(value = "id") long id, @RequestParam(value = "name") String reportName) {
        ResponceReportDto responsePostDto;
        HttpStatus httpStatus;

        try {
            ReportDto newReportDto = reportService.findById(id);
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