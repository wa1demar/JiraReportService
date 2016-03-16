package com.swansoftwaresolutions.jirareport.domain.repository.impl;


import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.swansoftwaresolutions.jirareport.domain.entity.Comment;
import com.swansoftwaresolutions.jirareport.domain.repository.CommentRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
public class CommentRepositoryImplTest extends AbstractDbTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testAddNewComment() throws Exception {
        Comment comment = new Comment();
        comment.setReportId(1L);
        comment.setSprintId(2L);
        comment.setCreator("User 6");
        comment.setText("Hello 6");
        comment.setCreatedDate(new Date());

        comment = commentRepository.add(comment);

        assertNotNull(comment.getId());
    }

    @Test
    @Transactional
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testFindAllComment() throws Exception {
        List<Comment> comments = commentRepository.findAll();
        assertNotNull(comments);
        assertNotNull(comments.size());
    }

//    @Test
//    public void testFindCommentById() throws Exception {
//        Comment comment = commentRepository.findById(1L);
//
//        assertNotNull(comment);
//        assertEquals("Hello 1", comment.getText());
//        assertEquals("User1", comment.getCreator());
//    }
//
//    @Test
//    public void testFindCommentByWrongId() throws Exception {
//        Comment comment = commentRepository.findById(10L);
//
//        assertNull(comment);
//
//    }
//
//    @Test
//    public void testFindCommentByReportId() throws Exception {
//        List<Comment> comments = commentRepository.findByReportId(2L);
//        assertNotNull(comments);
//        assertEquals(2, comments.size());
//    }
//
//    @Test
//    public void testFindCommentByWrongReportId() throws Exception {
//        List<Comment> comments = commentRepository.findByReportId(20L);
//        assertNotNull(comments);
//        assertEquals(0, comments.size());
//    }

//    @Test
//    public void testUpdateComments() throws Exception {
//        Comment comment = commentRepository.findById(1L);
//        assertNotNull(comment);
//
//        comment.setCreator("New Creator");
//
//        Comment updatedComment = commentRepository.update(comment);
//        assertNotNull(updatedComment);
//        assertEquals(comment.getId(), updatedComment.getId());
//        assertEquals("New Creator", updatedComment.getCreator());
//
//    }
//
//    @Test(expected = NoSuchEntityException.class)
//    public void testUpdateWrongComment() throws Exception {
//        Comment comment = new Comment();
//        comment.setReportId(4L);
//        comment.setSprintId(3L);
//        comment.setCreator("User 6");
//        comment.setText("Hello 6");
//        comment.setCreatedDate(new Date());
//
//        commentRepository.update(comment);
//
//    }
//
//    @Test
//    public void testDeleteComment() throws Exception {
//        Comment comment = commentRepository.findById(1L);
//        assertNotNull(comment);
//
//        commentRepository.delete(comment);
//        assertNull(commentRepository.findById(1L));
//        assertEquals(4, commentRepository.findAll().size());
//    }
//
//    @Test(expected = NoSuchEntityException.class)
//    public void testDeleteWrongComment() throws Exception {
//        Comment comment = new Comment();
//        comment.setId(15L);
//
//        commentRepository.delete(comment);
//        assertEquals(5, commentRepository.findAll().size());
//
//    }
//
//    @Test
//    public void testDeleteCommentById() throws Exception {
//        Comment comment = commentRepository.findById(1L);
//        assertNotNull(comment);
//
//        commentRepository.delete(comment.getId());
//        assertNull(commentRepository.findById(1L));
//        assertEquals(4, commentRepository.findAll().size());
//    }
//
//    @Test
//    public void testDeleteCommentsByReportId() throws Exception {
//        commentRepository.deleteByReportId(2L);
//        assertEquals(3, commentRepository.findAll().size());
//        assertEquals(0, commentRepository.findByReportId(2L).size());
//    }
//
//    @Test(expected = NoSuchEntityException.class)
//    public void testDeleteCommentsByWrongReportId() throws Exception {
//        commentRepository.deleteByReportId(5L);
//
//    }
//
//    @Test
//    public void testDeleteAllComments() throws Exception {
//        commentRepository.deleteAll();
//        assertEquals(0, commentRepository.findAll().size());
//    }
}
