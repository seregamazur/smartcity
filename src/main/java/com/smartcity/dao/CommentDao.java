package com.smartcity.dao;

import com.smartcity.domain.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao {

    Comment create(Comment comment);

    Comment get(long id);

    Comment update(Comment comment);

    boolean delete(long id);
}
