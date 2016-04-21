package com.swansoftwaresolutions.jirareport.domain.repository.impl;


import com.github.springtestdbunit.annotation.*;
import com.swansoftwaresolutions.jirareport.domain.entity.Comment;
import com.swansoftwaresolutions.jirareport.domain.repository.AbstractDbTest;
import com.swansoftwaresolutions.jirareport.domain.repository.CommentRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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
//    @Transactional
//    @DatabaseSetup(value = "/dbtest/comment/sampleDataStart.xml", type = DatabaseOperation.CLEAN_INSERT)
//    @ExpectedDatabase("/dbtest/comment/expectedData.xml")
//    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testAddNewComment() throws Exception {
        Comment comment = createComment(1L);

        assertNotNull(comment.getId());
    }

    private Comment createComment(Long reportd) {
        Comment comment = new Comment();
        comment.setReportId(reportd);
        comment.setSprintId(1L);
        comment.setCreator("Test User");
        comment.setText("Test comment text");
        comment.setCreatedDate(new Date());

        comment = commentRepository.add(comment);
        return comment;
    }

    @Test
    @Transactional
//    @DatabaseSetup("/dbtest/comment/sampleDataById.xml")
//    @ExpectedDatabase("/dbtest/comment/expectedDataById.xml")
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testFindById() throws Exception {
        Comment comment = commentRepository.findById(createComment(1L).getId());

        assertNotNull(comment.getId());
        assertEquals("Test comment text", comment.getText());
        assertEquals("Test User", comment.getCreator());
    }

    @Test
    @Transactional
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testFindAllComment() throws Exception {
        List<Comment> comments = commentRepository.findAll();
        assertTrue(comments.size()>0);
    }


    @Test
    @Transactional
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testFindCommentByWrongId() throws Exception {
        Comment comment = commentRepository.findById(100L);

        assertNull(comment);
    }

    @Test
    @Transactional
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testFindCommentByReportId() throws Exception {
        insertComments();
        List<Comment> comments = commentRepository.findByReportId(1L);
        assertTrue(comments.size()>0);
        assertEquals(1, comments.size());
    }

    private void insertComments() {
        for (int i = 1; i<5; i++ ){
            createComment(new Long(i));
        }
    }

    @Test
    @Transactional
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testFindCommentByWrongReportId() throws Exception {
        insertComments();
        List<Comment> comments = commentRepository.findByReportId(20L);
        assertTrue(comments.size()==0);
        assertEquals(0, comments.size());
    }

    @Test
    @Transactional
    @DatabaseTearDown(value = {"/dbtest/comment/expectedData.xml"}, type = DatabaseOperation.DELETE_ALL)
    public void testUpdateComments() throws Exception {
        Comment comment = createComment(1L);

        comment.setCreator("New Creator");

        Comment updatedComment = commentRepository.update(comment);
        assertTrue(updatedComment.getId()>0);
        assertEquals(comment.getId(), updatedComment.getId());
        assertEquals("New Creator", updatedComment.getCreator());

    }

    @Test(expected = NoSuchEntityException.class)
    public void testUpdateWrongComment() throws Exception {
        Comment comment = new Comment();
        comment.setReportId(4L);
        comment.setSprintId(3L);
        comment.setCreator("User 6");
        comment.setText("Hello 6");
        comment.setCreatedDate(new Date());

        commentRepository.update(comment);

    }

    @Test
    public void testDeleteComment() throws Exception {
        Comment comment = createComment(1L);

        commentRepository.delete(comment);
        assertNull(commentRepository.findById(comment.getId()));
    }

    @Test(expected = NoSuchEntityException.class)
    public void testDeleteWrongComment() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);

        commentRepository.delete(comment);
    }

    @Test
    public void testDeleteCommentById() throws Exception {
        Comment comment = createComment(1L);

        commentRepository.delete(comment.getId());
        assertNull(commentRepository.findById(comment.getId()));
    }

    @Test
    public void testDeleteCommentsByReportId() throws Exception {
        insertComments();
        List<Comment> comments = commentRepository.findByReportId(1L);
        assertTrue(comments.size()>0);

        commentRepository.deleteByReportId(1L);
        assertTrue(commentRepository.findByReportId(1L).size()==0);
    }

    @Test
    public void testDeleteAllComments() throws Exception {
       commentRepository.deleteAll();
       assertTrue(commentRepository.findAll().size()==0);
    }
}
