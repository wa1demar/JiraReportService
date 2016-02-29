package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * @author Vladimir Martynyuk
 */
public class CommentTest extends Mockito {

    @Test
    public void findByIdTest() throws Exception {
        CommentRepository repository = mock(CommentRepository.class);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setCreator("Creator");

        when(repository.findById(1L)).thenReturn(comment);
        assertEquals(repository.findById(1L).getId(), new Long(1));
    }
}
