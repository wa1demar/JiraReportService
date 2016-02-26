package com.swansoftwaresolutions.jirareport.core.repository;


import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.dbunit.annotation.ExpectedDataSet;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class CommentRepositoryImplTest extends AbstractDbTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @ExpectedDataSet
    public void commentMayBeAdded() throws Exception {
        Comment comment = commentRepository.createComment(new Comment(1L, 1L, "Text", "Creator", new Date()));
        assertNotNull(comment.getId());
    }
}
