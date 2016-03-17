package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.responce.ResponceReportDto;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequestMapping(value = "/v1/reports", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllReports() {
        ReportListDto reportDtos = reportService.retrieveAllReportsList();
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports", method = RequestMethod.POST)
    private ResponseEntity<ReportDto> addReport(@Valid @RequestBody NewReportDto newReportDto) throws NoSuchEntityException {
        ReportDto reportDto = reportService.add(newReportDto);

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/{id}", method = RequestMethod.GET)
    private ResponseEntity<ReportDto> getReportById(@PathVariable("id") long id) throws NoSuchEntityException {
        ReportDto reportDto = reportService.retrieveReportByID(id);

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/reports/{id}", method = RequestMethod.PUT)
    private ResponseEntity<ReportDto> updateReport(@PathVariable("id") long id, @Valid @RequestBody NewReportDto reportDto) throws NoSuchEntityException {
        ReportDto updatedReport = reportService.update(reportDto, id);

        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/reports/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<ReportDto> deleteReport(@PathVariable("id") long id) throws NoSuchEntityException {

        reportService.delete(id);

        return new ResponseEntity<>(new ReportDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/closed", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllClosedReports() {
        ReportListDto reportDtos = reportService.retrieveAllClosedReportsList();
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

//    @RequestMapping(value = "/v1/report/copy", method = RequestMethod.POST)
//    private ResponseEntity<ResponceReportDto> makeReportCopyById(@RequestParam(value = "id") long id, @RequestParam(value = "name") String reportName) {
//        ResponceReportDto responsePostDto;
//        HttpStatus httpStatus;
//
////        try {
////            ReportDto newReportDto = reportService.findById(id);
////            newReportDto.setTitle(reportName);
////            newReportDto.setId(null);
////            if (reportService.add(newReportDto) != null) {
////                responsePostDto = new ResponceReportDto(true, "Report added successfully.");
////                httpStatus = HttpStatus.OK;
////            } else {
////                responsePostDto = new ResponceReportDto(false, "Can't added Report.");
////                httpStatus = HttpStatus.NOT_FOUND;
////            }
////        } catch (NoSuchEntityException e) {
////            e.printStackTrace();
////            responsePostDto = new ResponceReportDto(false, "Can't added Report.");
////            httpStatus = HttpStatus.NOT_FOUND;
////        }
//
//        return new ResponseEntity<>(responsePostDto, httpStatus);
//    }


}