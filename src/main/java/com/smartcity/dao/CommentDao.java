package com.smartcity.dao;

import com.smartcity.domain.Comment;

public interface CommentDao {

    Comment create(Comment comment);

    Comment get(Long id);

    Comment update(Comment comment);

    boolean delete(Long id);
}
