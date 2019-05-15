package com.smartcity.dao;

import com.smartcity.domain.Comment;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;

public class CommentDaoImpl implements CommentDao {

    private static final Logger       logger = LoggerFactory.getLogger(CommentDaoImpl.class);
    private              JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public Comment create(Comment comment) {
        try {
            LocalDateTime createdDate = LocalDateTime.now();
            GeneratedKeyHolder holder = new GeneratedKeyHolder();

            jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(
                                Queries.SQL_CREATE,
                                Statement.RETURN_GENERATED_KEYS
                        );
                        ps.setString(1, comment.getDescription());
                        ps.setObject(2, createdDate);
                        ps.setObject(3, createdDate);
                        ps.setLong(4, comment.getUserId());
                        ps.setLong(5, comment.getTaskId());

                        return ps;
                    },
                    holder
            );
            comment.setUpdatedDate(createdDate);
            comment.setCreatedDate(createdDate);
            comment.setId(holder.getKey().longValue());

            return comment;
        } catch (Exception e) {
            logger.error("Can't create Comment:{}.Error:{}", comment, e.getMessage());
            throw new DbOperationException("Can't create comment by id=" + comment.getId() +
                    "Transaction:" + comment);
        }
    }

    @Override
    public Comment get(Long id) {
        try {
            Comment comment = jdbcTemplate.queryForObject(Queries.SQL_GET_BY_ID, CommentMapper.getInstance(), (Long) id);
            return comment;
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Comment not found!.Can't get comment by id={}. Error:", id, ex.getMessage());
            throw new NotFoundException("Comment not found!.Can't get comment by id=" + id);
        } catch (Exception e) {
            logger.error("Can't get comment by id={}. Error: ", id, e);
            throw new DbOperationException("Can't get comment.");
        }
    }

    @Override
    public Comment update(Comment comment) {
        int rowsAffected;

        try {
            LocalDateTime updatedDate = LocalDateTime.now();

            rowsAffected = jdbcTemplate.update(Queries.SQL_UPDATE,
                    comment.getDescription(),
                    updatedDate,
                    comment.getUserId(),
                    comment.getTaskId(),
                    comment.getId()
            );
            comment.setUpdatedDate(updatedDate);

        } catch (Exception e) {
            logger.error("Update comment (id = {}) exception. Message: {}", comment.getId(), e.getMessage());
            throw new DbOperationException("Update comment exceptions");
        }

        if (rowsAffected < 1) {
            logger.error("Comment not found!.Can't update comment by id=" + comment.getId());
            throw new NotFoundException("Comment not found!.Can't update comment by id=" + comment.getId());
        }

        return comment;
    }

    @Override
    public boolean delete(Long id) {
        int rowsAffected;

        try {
            rowsAffected = jdbcTemplate.update(Queries.SQL_DELETE, id);
        } catch (Exception e) {
            logger.error("Delete comment (id = {}) exception. Message: {}", id, e.getMessage());
            throw new DbOperationException("Delete comment exceptions");
        }

        if (rowsAffected < 1) {
            logger.error("Comment not found!.Can't delete comment by id={}. Error:", id);
            throw new NotFoundException("Comment not found!.Can't get comment by id=" + id);
        } else {
            return true;
        }
    }

    class Queries {
        public final static String SQL_CREATE = "INSERT INTO Comments(description, created_date, updated_date, user_id, task_id)" +
                " VALUES (?,?,?,?,?)";

        public final static String SQL_GET_BY_ID = "Select * from Comments where id = ?";

        public final static String SQL_UPDATE = "Update Comments set description = ? ,updated_date = ?, user_id = ?, " +
                "task_id = ? where id = ?";
        public final static String SQL_DELETE = "Delete from Comments where id = ?";
    }
}
