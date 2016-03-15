package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.report.*;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.ReportBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Vladimir Martynyuk
 */
public class ReportServiceImplTest extends AbstractServiceImplTest {

    @Autowired
    ReportRepository reportRepositoryMock;

    @Autowired
    ReportService reportService;

    @Before
    public void setUp() throws Exception {
        reportRepositoryMock = mock(ReportRepository.class);
        ReflectionTestUtils.setField(reportService, "reportRepository", reportRepositoryMock);
    }

    @Test
    public void testRetrieveAllReportList() throws Exception {
        List<Report> model = new ArrayList<>();
        when(reportRepositoryMock.findAll()).thenReturn(model);

        ReportListDto expected = new ReportListDto();
        ReportListDto actual = reportService.retrieveAllReportsList();

        verify(reportRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(reportRepositoryMock);

        assertEquals(expected, actual);
    }

    @Test
    public void testRetrieveReportByID() throws Exception {
        Report model = new ReportBuilder().id(1L).boardId(2L).creator("Username").isClosed(false).title("report").build();

        ReportDto expected = new ReportDtoBuilder().id(1L).boardId(2L).creator("Username").closed(false).title("report").build();

        when(reportRepositoryMock.findById(1L)).thenReturn(model);

        ReportDto actual = reportService.retrieveReportByID(1L);

        verify(reportRepositoryMock, times(1)).findById(1L);
        verifyNoMoreInteractions(reportRepositoryMock);

        assertEquals(expected, actual);

    }

    @Test
    public void testAddNewReport() throws Exception {
        Report model = new ReportBuilder().boardId(2L).creator("Username").isClosed(false).title("report").build();
        NewReportDto newModel = new NewReportDtoBuilder().boardId(2L).creator("Username").title("report").build();
        Report returnedModel = new ReportBuilder().id(1L).boardId(2L).creator("Username").isClosed(false).title("report").build();

        ReportDto expected = new ReportDtoBuilder().id(1L).boardId(2L).creator("Username").closed(false).title("report").build();

        when(reportRepositoryMock.add(model)).thenReturn(returnedModel);

        ReportDto actual = reportService.add(newModel);

        verify(reportRepositoryMock, times(1)).add(model);
        verifyNoMoreInteractions(reportRepositoryMock);

        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateReport() throws Exception {
        Report model = new ReportBuilder().id(1L).boardId(2L).creator("new Username").isClosed(true).title("updated report").build();
        NewReportDto newModel = new NewReportDtoBuilder().boardId(2L).creator("new Username").title("updated report").build();

        ReportDto expected = new ReportDtoBuilder().id(1L).boardId(2L).creator("new Username").closed(true).title("updated report").build();

        when(reportRepositoryMock.update(model)).thenReturn(model);

        ReportDto actual = reportService.update(newModel, 1L);

        verify(reportRepositoryMock, times(1)).update(model);
        verifyNoMoreInteractions(reportRepositoryMock);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCreator(), actual.getCreator());
        assertEquals(expected.getTitle(), actual.getTitle());

    }

    @Test
    public void testDeleteReport() throws Exception {
        Report model = new ReportBuilder().id(1L).boardId(2L).creator("Username").isClosed(true).title("report").build();

        when(reportRepositoryMock.delete(1L)).thenReturn(model);

        ReportDto expected = new ReportDtoBuilder().id(1L).boardId(2L).creator("Username").closed(true).title("report").build();

        ReportDto actual = reportService.delete(1L);

        verify(reportRepositoryMock, times(1)).delete(1L);
        verifyNoMoreInteractions(reportRepositoryMock);

        assertEquals(expected, actual);
    }
}