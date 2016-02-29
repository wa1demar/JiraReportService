package com.swansoftwaresolutions.jirareport.core.repository.impl;


import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.repository.CommentRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
@SqlGroup({
        @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class CommentRepositoryImplTest extends AbstractDbTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void commentMayBeAdded() throws Exception {
        Comment comment = new Comment();
        comment.setReportId(28L);
        comment.setSprintId(2L);
        comment.setCreator("Creator");
        comment.setText("Text");
        comment.setCreatedDate(new Date());

        Comment newComment = commentRepository.add(comment);

        assertNotNull(newComment.getId());
        assertEquals(newComment.getCreator(), "Creator");
    }


    @Test
    public void commentMeyBeSelected() throws Exception {
        assertNotNull(commentRepository.findAll());
    }

    @Test
    public void commentMayBeFoundById() throws Exception {
        assertNotNull(commentRepository.findById(28L));
    }

    @Test
    public void commentMayBeFoundByReportId() throws Exception {
        assertNotNull(commentRepository.findByReportId(2L));
    }

    @Test
    public void commentMayBeUpdated() throws Exception {
        Comment comment = commentRepository.findById(28L);
        assertNotNull(comment);

        comment.setCreator("New Creator");

        Comment updatedComment = commentRepository.update(comment);
        assertNotNull(updatedComment);
        assertEquals(comment.getId(), updatedComment.getId());
        assertEquals(updatedComment.getCreator(), "New Creator");

    }

    @Test
    public void commentMayBeDeleted() throws Exception {
        Comment comment = commentRepository.findById(28L);
        assertNotNull(comment);

        commentRepository.delete(comment);
        assertNull(commentRepository.findById(28L));
    }

    @Test
    @Rollback
    public void commentMayBeDeletedById() throws Exception {
        Comment comment = commentRepository.findById(28L);
        assertNotNull(comment);

        commentRepository.delete(comment.getId());
        assertNull(commentRepository.findById(28L));
    }

    @Test
    @Rollback
    public void commentMayBeDeletedByReportId() throws Exception {
        Comment comment = commentRepository.findById(28L);
        assertNotNull(comment);

        commentRepository.deleteByReportId(comment.getReportId());
        assertNull(commentRepository.findByReportId(comment.getReportId()));
    }

    @Test
    public void allCommentsMayBeDeleted() throws Exception {
        commentRepository.deleteAll();
        assertEquals(commentRepository.findAll().size(), 0);
    }
}
