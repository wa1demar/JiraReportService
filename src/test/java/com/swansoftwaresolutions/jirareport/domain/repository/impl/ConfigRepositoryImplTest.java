package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.swansoftwaresolutions.jirareport.domain.entity.Config;
import com.swansoftwaresolutions.jirareport.domain.repository.AbstractDbTest;
import com.swansoftwaresolutions.jirareport.domain.repository.ConfigRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Vladimir Martynyuk
 */
public class ConfigRepositoryImplTest extends AbstractDbTest {

    @Autowired
    ConfigRepository configRepository;

    DateFormat dataFormatnew;

    @Test
    public void testUpdateConfig() throws Exception {
        dataFormatnew = new SimpleDateFormat("MM-dd-yyyy");

        Config config = configRepository.findById(1L);
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");

        Config updatedConfig = configRepository.update(config);
        assertNotNull(updatedConfig.getId());
        assertEquals("Bug1", updatedConfig.getBugName());
    }

    @Test(expected = NullPointerException.class)
    public void testWrongParseDataUpdateConfig() throws Exception {
        Config config = configRepository.findById(1L);
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");

        configRepository.update(config);
    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongConfig() throws Exception {
        Config config = new Config();
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
    @DatabaseSetup("/dbtest/config/sampleData.xml")
    @DatabaseTearDown(value = "/dbtest/config/sampleData.xml", type = DatabaseOperation.DELETE_ALL)
    public void testFindConfigById() throws Exception {
        Config config = configRepository.findById(1L);

        assertNotNull(config);
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
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");

        configRepository.delete(config);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteWrongParseDataConfig() throws Exception {
        Config config = new Config();
        config.setAgileDoneName("Dev Done1");
        config.setBugName("Bug1");
        config.setNonWorkingDays("Sunday");

        configRepository.delete(config);
    }

}
