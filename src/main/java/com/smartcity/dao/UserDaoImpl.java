package com.smartcity.dao;


import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.UserMapper;
import com.smartcity.utils.EncryptionUtil;
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


public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public User create(User user) {
        try {
            LocalDateTime currentDate = LocalDateTime.now();

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            String encryptedPassword = EncryptionUtil.encryptPassword(user.getPassword());

            jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(
                                Queries.SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, user.getEmail());
                        ps.setString(2, encryptedPassword);
                        ps.setString(3, user.getSurname());
                        ps.setString(4, user.getName());
                        ps.setString(5, user.getPhoneNumber());
                        ps.setBoolean(6, true);
                        ps.setObject(7, currentDate);
                        ps.setObject(8, currentDate);

                        return ps;
                    },
                    holder
            );

            user.setCreatedDate(currentDate);
            user.setUpdatedDate(currentDate);
            user.setActive(true);
            user.setId(holder.getKey().longValue());

            return user;
        }
        catch (Exception e) {
            logger.error("Create user (id = {}) exception. Message: {}", user.getId(), e.getMessage());
            throw new DbOperationException("Create user exception");
        }
    }

    @Override
    public User get(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(Queries.SQL_SELECT_USER, UserMapper.getInstance(), (Long) id);
            return user;
        }
        catch (EmptyResultDataAccessException | NotFoundException ex) {
            throw getAndLogUserNotFoundException(id);
        }
        catch (Exception e) {
            logger.error("Get user (id = {}) exception. Message: {}", id, e.getMessage());
            throw new DbOperationException("Get user exception");
        }
    }

    @Override
    public User update(User user) {
        // Throwing not found exception, if user id is incorrect
        try {
            this.get(user.getId());
        }
        catch (NotFoundException ex) {
            throw getAndLogUserNotFoundException(user.getId());
        }

        try {
            LocalDateTime updatedDate = LocalDateTime.now();


            jdbcTemplate.update(
                    Queries.SQL_UPDATE_USER,
                    user.getEmail(),
                    EncryptionUtil.encryptPassword(user.getPassword()),
                    user.getSurname(),
                    user.getName(),
                    user.getPhoneNumber(),
                    user.isActive(),
                    updatedDate,
                    user.getId());

            user.setUpdatedDate(updatedDate);

            return user;
        }
        catch (Exception e) {
            logger.error("Update user (id = {}) exception. Message: {}", user.getId(), e.getMessage());
            throw new DbOperationException("Update user exceptions");
        }
    }

    @Override
    public boolean delete(Long id) {
        int rowsAffected;
        try {
            rowsAffected = jdbcTemplate.update(Queries.SQL_DELETE_USER, id);
        }
        catch (Exception e) {
            logger.error("Delete user (id = {}) exception. Message: {}", id, e.getMessage());
            throw new DbOperationException("Delete user exceptions");
        }

        if (rowsAffected < 1) {
            throw getAndLogUserNotFoundException(id);
        }
        else {
            return true;
        }
    }

    private NotFoundException getAndLogUserNotFoundException(Long id) {
        NotFoundException notFoundException = new NotFoundException("User not found");
        logger.error("Runtime exception. User not found id = {}. Message: {}", id, notFoundException.getMessage());
        return notFoundException;
    }

    class Queries {
        static final String SQL_DELETE_USER = "DELETE FROM Users WHERE id = ?";

        static final String SQL_UPDATE_USER = "UPDATE Users SET " +
                "email = ?, password = ?, surname = ?," +
                " name = ?, phone_number = ?, active = ?, updated_date = ? WHERE id = ?;";

        static final String SQL_SELECT_USER = "SELECT * FROM Users WHERE id = ?";

        static final String SQL_CREATE_USER = "" +
                "INSERT INTO Users(email, password, surname," +
                " name, phone_number, active, created_date, updated_date)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    }

}

