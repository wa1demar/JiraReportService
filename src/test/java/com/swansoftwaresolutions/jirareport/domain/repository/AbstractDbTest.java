package com.swansoftwaresolutions.jirareport.domain.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.swansoftwaresolutions.jirareport.config.BusinessContext;
import com.swansoftwaresolutions.jirareport.config.HibernateConfig;
import com.swansoftwaresolutions.jirareport.config.HibernateTextConfig;
import com.swansoftwaresolutions.jirareport.config.TestContext;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BusinessContext.class, HibernateTextConfig.class, TestContext.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public abstract class AbstractDbTest extends Assert {


}
