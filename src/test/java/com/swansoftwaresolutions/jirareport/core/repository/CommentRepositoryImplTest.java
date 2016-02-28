package com.swansoftwaresolutions.jirareport.core.repository;


import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class CommentRepositoryImplTest extends AbstractDbTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void commentMayBeAdded() throws Exception {
        Comment comment = new Comment();
        comment.setReportId(1L);
        comment.setSprintId(1L);
        comment.setCreator("Creator");
        comment.setText("Text");
        comment.setCreatedDate(new Date());

        Comment newComment = commentRepository.createComment(comment);

        assertNotNull(newComment.getId());
        assertEquals(newComment.getCreator(), "Creator");
    }

    @Test
    public void commentMayBeFoundById() throws Exception {
        assertNotNull(commentRepository.findById(1L));
    }

    @Test
    public void commentMayBeFoundByReportId() throws Exception {
        assertNotNull(commentRepository.findByReportId(1L));
    }
}
