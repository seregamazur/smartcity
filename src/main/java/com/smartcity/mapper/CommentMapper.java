package com.smartcity.mapper;

import com.smartcity.domain.Comment;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
