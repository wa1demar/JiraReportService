package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.config.HibernateConfig;
import com.swansoftwaresolutions.jirareport.config.TestContext;
import com.swansoftwaresolutions.jirareport.config.WebConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, HibernateConfig.class, WebConfig.class})
@WebAppConfiguration
public abstract class AbstractRestControllerTest {
}
