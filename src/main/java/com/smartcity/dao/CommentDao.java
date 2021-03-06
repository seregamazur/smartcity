package com.smartcity.dao;

import com.smartcity.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    Comment create(Comment comment);

    Comment findById(Long id);

    Comment update(Comment comment);

    boolean delete(Long id);

    List<Comment> findAllByTaskId(Long id);

    List<Comment> findAllByUserId(Long id);

}
