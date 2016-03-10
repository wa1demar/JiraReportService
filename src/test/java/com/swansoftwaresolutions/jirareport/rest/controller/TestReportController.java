package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminreportDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vladimir Martynyuk
 */
public class TestReportController extends AbstractRestControllerTest {

    @Autowired
    private ReportService reportServiceMock;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(reportServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllReports() throws Exception {
        ReportDto firstReport = new ReportDtoBuilder()
                .id(1L)
                .title("Report 1")
                .creator("Creator")
                .creatorId(2L)
                .boardId(3L)
                .createdDate(new Date())
                .updatedDate(new Date())
                .closed(false)
                .admins(new ArrayList<AdminReportDto>() {{
                    add(new AdminreportDtoBuilder()
                            .id(1L)
                            .login("Login 1")
                            .fullName("Full Name1")
                            .build());
                    add(new AdminreportDtoBuilder()
                            .id(2L)
                            .login("Login 2")
                            .fullName("Full Name2")
                            .build());
                }})
                .build();

        ReportDto secondReport = new ReportDtoBuilder()
                .id(2L)
                .title("Report 2")
                .creator("Creator 2")
                .creatorId(1L)
                .boardId(2L)
                .createdDate(new Date())
                .updatedDate(new Date())
                .closed(false)
                .admins(new ArrayList<AdminReportDto>() {{
                    add(new AdminreportDtoBuilder()
                            .id(1L)
                            .login("Login 1")
                            .fullName("Full Name1")
                            .build());
                }})
                .build();


        ReportListDto expectedReportListDto = new ReportListDtoBuilder()
                .reportsDto(new ArrayList<ReportDto>() {{
                    add(firstReport);
                    add(secondReport);
                }})
                .build();


        when(reportServiceMock.retrieveAllReportsList()).thenReturn(expectedReportListDto);

        mockMvc.perform(get("/rest/v1/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("Report 1")))
                .andExpect(jsonPath("$[1].title", is("Report 2")))
                .andExpect(jsonPath("$[0].creator", is("Creator 1")))
                .andExpect(jsonPath("$[1].creator", is("Creator 2")))
                ;



    }
}
