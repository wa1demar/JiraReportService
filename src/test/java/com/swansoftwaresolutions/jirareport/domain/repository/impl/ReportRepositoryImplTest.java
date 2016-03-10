package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Admin;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/report_before.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/report_after.sql")
})
public class ReportRepositoryImplTest extends AbstractDbTest {

    @Autowired
    ReportRepository reportRepository;

    DateFormat dataFormatnew;

    @Test
    public void testAddNewReport() throws Exception {
        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");

        Report report = new Report();
        report.setTitle("Test Report A");
        report.setCreatedDate(new Date());
        report.setCreator("Test Creator");
        report.setTypeId(0L);
        report.setBoardId(2L);
        report.setCreatorId(34L);

        Admin admin1 = new Admin();
        admin1.setFullName("admin report1");
        admin1.setLogin("adminreport1");

        Admin admin2 = new Admin();
        admin2.setFullName("admin report2");
        admin2.setLogin("adminreport2");

        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin1);
        adminList.add(admin2);

        report.setAdmins(adminList);

        Report newReport = reportRepository.add(report);

        assertNotNull(newReport.getId());
        assertEquals("Test Report A", newReport.getTitle());
        assertEquals("Test Creator", newReport.getCreator());
        assertNotNull(newReport.getAdmins());
        assertEquals(2, newReport.getAdmins().size());
        assertNotNull(newReport.getAdmins().get(0).getId());
    }

//    @Test(expected = NullPointerException.class)
//    public void testAddWrongParseDataNewConfig() throws Exception {
//        Config config = new Config();
//        config.setStoryPointsName("Story Point");
//        config.setAgileDoneName("Dev Done");
//        config.setBugName("Bug");
//        config.setNonWorkingDays("Sunday");
//        config.setAutoSyncTime(dataFormatnew.format(new Date()));
//
//        configRepository.add(config);
//    }
//
//    @Test
//    public void testUpdateConfig() throws Exception {
//        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");
//
//        Config config = configRepository.findById(1L);
//        config.setStoryPointsName("Story Point1");
//        config.setAgileDoneName("Dev Done1");
//        config.setBugName("Bug1");
//        config.setNonWorkingDays("Sunday");
//        config.setAutoSyncTime(dataFormatnew.format(new Date()));
//
//        Config updatedConfig = configRepository.update(config);
//        assertNotNull(updatedConfig.getId());
//        assertEquals("Story Point1", updatedConfig.getStoryPointsName());
//        assertEquals("Bug1", updatedConfig.getBugName());
//        assertEquals(dataFormatnew.format(new Date()), updatedConfig.getAutoSyncTime());
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void testWrongParseDataUpdateConfig() throws Exception {
//        Config config = configRepository.findById(1L);
//        config.setStoryPointsName("Story Point1");
//        config.setAgileDoneName("Dev Done1");
//        config.setBugName("Bug1");
//        config.setNonWorkingDays("Sunday");
//        config.setAutoSyncTime(dataFormatnew.format(new Date()));
//
//        configRepository.update(config);
//    }
//
//    @Test(expected = NoSuchEntityException.class)
//    public void testUpdateWrongConfig() throws Exception {
//        Config config = new Config();
//        config.setStoryPointsName("Story Point1");
//        config.setAgileDoneName("Dev Done1");
//        config.setBugName("Bug1");
//        config.setNonWorkingDays("Sunday");
//
//        configRepository.update(config);
//    }
//
//    @Test
//    public void testGetAllConfigs() throws Exception {
//        List<Config> configs = configRepository.findAll();
//        assertNotNull(configs);
//        assertEquals(5, configs.size());
//    }
//
//    @Test
//    public void testFindConfigById() throws Exception {
//        Config config = configRepository.findById(1L);
//
//        assertNotNull(config);
//        assertEquals("Story Point", config.getStoryPointsName());
//        assertEquals("Bug", config.getBugName());
//    }
//
//    @Test
//    public void testFindConfigByWrongId() throws Exception {
//        Config config = configRepository.findById(10L);
//        assertNull(config);
//    }
//
//    @Test(expected = NoSuchEntityException.class)
//    public void testDeleteConfigByWrongId() throws Exception {
//        configRepository.delete(10L);
//    }
//
//    @Test
//    public void testDeleteConfig() throws Exception {
//        Config config = configRepository.findById(1L);
//        assertNotNull(config);
//
//        configRepository.delete(config);
//        assertNull(configRepository.findById(1L));
//        assertEquals(4, configRepository.findAll().size());
//    }
//
//    @Test(expected = NoSuchEntityException.class)
//    public void testDeleteWrongConfig() throws Exception {
//
//        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");
//
//        Config config = new Config();
//        config.setStoryPointsName("Story Point1");
//        config.setAgileDoneName("Dev Done1");
//        config.setBugName("Bug1");
//        config.setNonWorkingDays("Sunday");
//        config.setAutoSyncTime(dataFormatnew.format(new Date()));
//
//        configRepository.delete(config);
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void testDeleteWrongParseDataConfig() throws Exception {
//        Config config = new Config();
//        config.setStoryPointsName("Story Point1");
//        config.setAgileDoneName("Dev Done1");
//        config.setBugName("Bug1");
//        config.setNonWorkingDays("Sunday");
//        config.setAutoSyncTime(dataFormatnew.format(new Date()));
//
//        configRepository.delete(config);
//    }
}
