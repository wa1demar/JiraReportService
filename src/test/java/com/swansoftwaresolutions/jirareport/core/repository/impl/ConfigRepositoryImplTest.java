package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Config;
import com.swansoftwaresolutions.jirareport.core.repository.ConfigRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/config_before.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/config_after.sql")
})
public class ConfigRepositoryImplTest extends AbstractDbTest {

    @Autowired
    ConfigRepository configRepository;

    DateFormat dataFormatnew;

    @Test
    public void testAddNewConfig() throws Exception {
        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");

        Config config = new Config();
        config.setStoryPointsName("Story Point");
        config.setAgileDoneName("Dev Done");
        config.setBugName("Bug");
        config.setNonWorkingDays("Sunday");
        config.setAutoSyncTime(dataFormatnew.format(new Date()));

        Config newConfig = configRepository.add(config);

        assertNotNull(newConfig.getId());
        assertEquals("Story Point", newConfig.getStoryPointsName());
        assertEquals("Dev Done", newConfig.getAgileDoneName());
        assertEquals("Bug", newConfig.getBugName());
        assertEquals("Sunday", newConfig.getNonWorkingDays());
        assertNotNull(newConfig.getAutoSyncTime());
    }

    @Test(expected = NullPointerException.class)
    public void testAddWrongParseDataNewConfig() throws Exception {
        Config config = new Config();
        config.setStoryPointsName("Story Point");
        config.setAgileDoneName("Dev Done");
        config.setBugName("Bug");
        config.setNonWorkingDays("Sunday");
        config.setAutoSyncTime(dataFormatnew.format(new Date()));

        configRepository.add(config);
    }

    @Test
    public void testUpdateConfig() throws Exception {
        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");

        Config config = configRepository.findById(1L);
        config.setStoryPointsName("Story Point1");
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");
        config.setAutoSyncTime(dataFormatnew.format(new Date()));

        Config updatedConfig = configRepository.update(config);
        assertNotNull(updatedConfig.getId());
        assertEquals("Story Point1", updatedConfig.getStoryPointsName());
        assertEquals("Bug1", updatedConfig.getBugName());
        assertEquals(dataFormatnew.format(new Date()), updatedConfig.getAutoSyncTime());
    }

    @Test(expected = NullPointerException.class)
    public void testWrongParseDataUpdateConfig() throws Exception {
        Config config = configRepository.findById(1L);
        config.setStoryPointsName("Story Point1");
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");
        config.setAutoSyncTime(dataFormatnew.format(new Date()));

        configRepository.update(config);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongConfig() throws Exception {
        Config config = new Config();
        config.setStoryPointsName("Story Point1");
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");

        configRepository.update(config);
    }

    @Test
    public void testGetFirst() throws Exception {
        Config configs = configRepository.findFirst();
        assertNotNull(configs);
    }

    @Test
    public void testFindConfigById() throws Exception {
        Config config = configRepository.findById(1L);

        assertNotNull(config);
        assertEquals("Story Point", config.getStoryPointsName());
        assertEquals("Bug", config.getBugName());
    }

    @Test
    public void testFindConfigByWrongId() throws Exception {
        Config config = configRepository.findById(10L);
        assertNull(config);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteConfigByWrongId() throws Exception {
        configRepository.delete(10L);
    }

    @Test
    public void testDeleteConfig() throws Exception {
        Config config = configRepository.findById(1L);
        assertNotNull(config);

        configRepository.delete(config);
        assertNull(configRepository.findById(1L));
//        assertEquals(4, configRepository.findFirst().size());
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteWrongConfig() throws Exception {

        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");

        Config config = new Config();
        config.setStoryPointsName("Story Point1");
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");
        config.setAutoSyncTime(dataFormatnew.format(new Date()));

        configRepository.delete(config);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteWrongParseDataConfig() throws Exception {
        Config config = new Config();
        config.setStoryPointsName("Story Point1");
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");
        config.setAutoSyncTime(dataFormatnew.format(new Date()));

        configRepository.delete(config);
    }
}
