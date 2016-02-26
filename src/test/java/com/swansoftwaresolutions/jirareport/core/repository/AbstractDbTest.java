package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.config.BusinessContext;
import com.swansoftwaresolutions.jirareport.config.JPAConfig;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.DatabaseUnitils;
import org.unitils.database.SQLUnitils;

/**
 * @author Vladimir Martynyuk
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@Transactional
@PropertySource("classpath:database.properties")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {BusinessContext.class, JPAConfig.class})
public abstract class AbstractDbTest extends Assert {

    protected long getNumberOfRecords(String tableName) {
        return SQLUnitils.getItemAsLong("select count(*) from " + tableName, DatabaseUnitils.getDataSource());
    }

}
