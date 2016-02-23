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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {BusinessContext.class, JPAConfig.class})
@Transactional
public class CommentRepositoryImplTest {

    private static org.apache.log4j.Logger log = Logger.getLogger(CommentRepositoryImplTest.class);

    private static long COMMENT_TEST_ID = 83L;
    private static long REPORT_TEST_ID = 7L;
    private static long NEW_COMMENT_TEST_ID = 113L;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void testGetAllComments() {
        List<Comment> comments = commentRepository.getAllComments();

        assertEquals(94, comments.size());
    }

    @Test
    public void testGetCommentById() {
        Comment comment = commentRepository.getCommentById(COMMENT_TEST_ID);

        assertNotNull(comment);
        assertTrue(comment.getReportId().equals(REPORT_TEST_ID));
        assertEquals("victorsavinov", comment.getCreator());
    }

    @Test
    public void testGetCommentByReportId() {
        List<Comment> comments = commentRepository.getCommentsByReportId(REPORT_TEST_ID);

        assertNotNull(comments);
        assertEquals(2, comments.size());
    }

    @Test
    public void testGetCreateComment() {
        Comment comment = new Comment();
        comment.setId(NEW_COMMENT_TEST_ID);
        comment.setCreator("Vladimir");
        comment.setReportId(REPORT_TEST_ID);
        comment.setSprintId(REPORT_TEST_ID);
        comment.setCreatedDate(new Date());

        commentRepository.createComment(comment);

        Comment newComment = commentRepository.getCommentById(NEW_COMMENT_TEST_ID);

        assertNotNull(newComment);
        assertTrue(comment.getReportId().equals(REPORT_TEST_ID));
        assertTrue(comment.getSprintId().equals(REPORT_TEST_ID));
        assertEquals("Vladimir", comment.getCreator());

        List<Comment> comments = commentRepository.getCommentsByReportId(REPORT_TEST_ID);

        assertNotNull(comments);
        assertEquals(3, comments.size());

    }


}
