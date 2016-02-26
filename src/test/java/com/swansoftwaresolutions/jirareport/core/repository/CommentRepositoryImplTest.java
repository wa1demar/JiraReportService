package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.config.*;
import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Vladimir Martynyuk
 */
public class CommentRepositoryImplTest extends AbstractDbTest {

    @Autowired
    private CommentRepository repository;

    @Test
    public void commentMayBeAdded() throws Exception {
        Comment comment = repository.createComment(new Comment(1L, 1L, "Text", "Creator", new Date()));
        assertNotNull(comment.getId());
    }
}
