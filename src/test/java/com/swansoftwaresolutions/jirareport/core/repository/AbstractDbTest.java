package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.config.BusinessContext;
import com.swansoftwaresolutions.jirareport.config.HibernateConfig;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BusinessContext.class, HibernateConfig.class })
public abstract class AbstractDbTest extends Assert {


}
