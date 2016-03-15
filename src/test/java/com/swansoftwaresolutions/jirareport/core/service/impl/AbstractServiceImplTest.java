package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.config.BusinessContext;
import com.swansoftwaresolutions.jirareport.config.HibernateConfig;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BusinessContext.class, HibernateConfig.class})
@ComponentScan(basePackages = {
        "com.swansoftwaresolutions.jirareport.core.service.impl"
})
public abstract class AbstractServiceImplTest {


}
