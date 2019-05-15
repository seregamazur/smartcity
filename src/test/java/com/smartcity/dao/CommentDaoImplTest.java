package com.smartcity.dao;

import com.smartcity.domain.Comment;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CommentDaoImplTest extends BaseTest {

    @Autowired
    private CommentDao commentDao;

    private Comment comment;

    @BeforeEach
    public void beforeEach() {
        LocalDateTime createdDate = LocalDateTime.now();
        comment = new Comment();
        comment.setDescription("Comment for comment");
        comment.setCreatedDate(createdDate);
        comment.setUpdatedDate(createdDate);
        comment.setTaskId(1L);
        comment.setUserId(1L);

        commentDao.create(comment);
    }

    @Test
    public void testCreateComment() {

        assertEquals(comment, commentDao.create(comment));

    }

    @Test
    public void testCreateComment_omittedNotNullFields() {
        Comment emptyComment = new Comment();
        assertThrows(DbOperationException.class, () -> commentDao.create(emptyComment));

    }

    @Test
    public void testCreateComment_invalidTaskId() {
        comment.setTaskId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    public void testCreateComment_missingTaskId() {
        comment.setTaskId(null);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    public void testCreateComment_invalidUserId() {
        comment.setUserId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    public void testCreateComment_missingUserId() {
        comment.setUserId(null);
        assertThrows(DbOperationException.class, () -> commentDao.create(comment));
    }

    @Test
    public void testGetComment() {
        Comment result = commentDao.get(comment.getId());
        assertThat(comment).
                isEqualToIgnoringGivenFields(result,
                        "createdDate", "updatedDate");
    }

    @Test
    public void testGetComment_invalidId() {
        assertThrows(NotFoundException.class, () -> commentDao.get(Long.MAX_VALUE));

    }

    @Test
    public void testUpdateComment() {
        commentDao.create(comment);

        Comment updatedComment = new Comment(comment.getId(), "Comment for Test$2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L, 1L);

        commentDao.update(updatedComment);
        Comment resultComment = commentDao.get(updatedComment.getId());
        assertThat(updatedComment).isEqualToIgnoringGivenFields(resultComment,
                "createdDate", "updatedDate");
    }

    @Test
    public void testUpdateComment_invalidId() {
        Comment updatedComment = new Comment(Long.MAX_VALUE, "Comment for Test",
                LocalDateTime.now(),
                LocalDateTime.now(),
                800000L, 44000L);

        assertThrows(NotFoundException.class, () -> commentDao.update(updatedComment));

    }

    @Test
    public void testDeleteComment() {
        assertTrue(commentDao.delete(comment.getId()));
    }

    @Test
    public void testDeleteComment_invalidId() {
        assertThrows(NotFoundException.class, () -> commentDao.delete(Long.MAX_VALUE));

    }

    @AfterEach
    public void afterEach() {
        clearTables("Comments");
    }

}
