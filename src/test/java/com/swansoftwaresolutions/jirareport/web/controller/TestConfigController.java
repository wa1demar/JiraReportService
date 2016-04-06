package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.config.*;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDtoBuilder;
import com.swansoftwaresolutions.jirareport.web.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, HibernateConfig.class, WebConfig.class})
@WebAppConfiguration
public class TestConfigController {

    private MockMvc mockMvc;

    @Autowired
    private ConfigService configServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(configServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllConfigs() throws Exception {
        ConfigDto firstConfig = new ConfigDtoBuilder()
                .id(1L)
                .agileDoneName("AgileDone")
                .jiraDevGroupName("JiraDevGroupName")
                .bugName("BugName")
                .nonWorkingDays("NonWorkingDays")
                .build();

        when(configServiceMock.retrieveConfig()).thenReturn(firstConfig);

        mockMvc.perform(get("/rest/v1/config"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.storyPointsName", is("Story Point")))
                .andExpect(jsonPath("$.agileDoneName", is("AgileDone")))
                .andExpect(jsonPath("$.jiraDevGroupName", is("JiraDevGroupName")))
                .andExpect(jsonPath("$.bugName", is("BugName")))
                .andExpect(jsonPath("$.nonWorkingDays", is("NonWorkingDays")))
                .andExpect(jsonPath("$.autoSyncTime", is("AutoSyncTime")))
                ;

        verify(configServiceMock, times(1)).retrieveConfig();
        verifyNoMoreInteractions(configServiceMock);


    }

    @Test
    public void testUpdateConfig() throws Exception {
        ConfigDto configDto = new ConfigDtoBuilder()
                .agileDoneName("AgileDone")
                .jiraDevGroupName("JiraDevGroupName")
                .bugName("BugName")
                .nonWorkingDays("NonWorkingDays")
                .build();

        ConfigDto updatedDto = new ConfigDtoBuilder()
                .agileDoneName("AgileDone 2")
                .jiraDevGroupName("JiraDevGroupName 2")
                .bugName("BugName 2")
                .nonWorkingDays("NonWorkingDays 2")
                .build();

        when(configServiceMock.update(configDto)).thenReturn(updatedDto);

        mockMvc.perform(patch("/rest/v1/config")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(configDto))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.storyPointsName", is("Story Point 2")))
                .andExpect(jsonPath("$.agileDoneName", is("AgileDone 2")))
                .andExpect(jsonPath("$.jiraDevGroupName", is("JiraDevGroupName 2")))
                .andExpect(jsonPath("$.bugName", is("BugName 2")))
                .andExpect(jsonPath("$.nonWorkingDays", is("NonWorkingDays 2")))
                .andExpect(jsonPath("$.autoSyncTime", is("AutoSyncTime 2")))
                ;

        ArgumentCaptor<ConfigDto> dtoCaptor = ArgumentCaptor.forClass(ConfigDto.class);
        verify(configServiceMock, times(1)).update(dtoCaptor.capture());
        verifyNoMoreInteractions(configServiceMock);

        ConfigDto dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getId(), is(1L));
        assertThat(dtoArgument.getAgileDoneName(), is("agileDoneName"));
        assertThat(dtoArgument.getJiraDevGroupName(), is("jiraDevGroupName"));
        assertThat(dtoArgument.getBugName(), is("bugName"));
        assertThat(dtoArgument.getNonWorkingDays(), is("nonWorkingDays"));

    }
}
