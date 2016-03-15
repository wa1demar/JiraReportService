package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.config.TestContext;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
        List<Report> expected = new ArrayList<>();
        when(reportRepositoryMock.findAll()).thenReturn(expected);

        ReportListDto actual = reportService.retrieveAllReportsList();

        verify(reportRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(reportRepositoryMock);

        assertEquals(expected, actual.getReports());
    }
}
