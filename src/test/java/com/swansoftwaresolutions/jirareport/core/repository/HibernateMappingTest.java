package com.swansoftwaresolutions.jirareport.core.repository;

import org.junit.Test;
import org.unitils.orm.hibernate.HibernateUnitils;

/**
 * @author Vladimir Martynyuk
 */
public class HibernateMappingTest extends AbstractDbTest {
    @Test
    public void testHibernateMapping() {
        HibernateUnitils.assertMappingWithDatabaseConsistent();
    }
}
