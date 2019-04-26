package com.smartcity.dao;

import com.smartcity.domain.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao {

    Comment save(Comment comment);

    Comment findById(Integer id);

    boolean deleteById(Integer id);
}
