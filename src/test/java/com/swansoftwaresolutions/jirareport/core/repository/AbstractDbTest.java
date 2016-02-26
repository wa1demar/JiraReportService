package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.config.BusinessContext;
import com.swansoftwaresolutions.jirareport.core.config.JPAConfig;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.UnitilsJUnit4TestClassRunner;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {BusinessContext.class, JPAConfig.class})
@Transactional
public abstract class AbstractDbTest extends Assert {

}
