package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.swansoftwaresolutions.jirareport.config.BusinessContext;
import com.swansoftwaresolutions.jirareport.config.HibernateConfig;
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
@ContextConfiguration(classes = { BusinessContext.class, HibernateConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public abstract class AbstractDbTest extends Assert {


}
