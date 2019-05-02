package com.smartcity.dao;

import com.smartcity.domain.Role;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.Optional;

public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Role> create(Role role) {
        try {
            this.jdbcTemplate.update(Queries.SQL_CREATE, role.getId(), role.getName(), CurrentDate.getCurDate(),
                    CurrentDate.getCurDate());
            return Optional.ofNullable(role);
        } catch (Exception e) {
            throw new DbOperationException("Create Role exception");
        }
    }

    public Optional<Role> get(long id) {
        try {
            Role role = this.jdbcTemplate.queryForObject(Queries.SQL_GET_BY_ID, new Object[]{id}, new RoleMapper());
            return Optional.ofNullable(role);
        } catch (Exception e) {
            throw new DbOperationException("Get Role Exception");
        }

    }

    public Optional<Role> update(Role role) {
        try {
            this.jdbcTemplate.update(Queries.SQL_UPDATE, role.getName(),
                    CurrentDate.getCurDate(), role.getId());
            return Optional.ofNullable(role);
        } catch (Exception e) {
            throw new DbOperationException("Update Role exception");
        }
    }

    public boolean delete(long id) {
        try {
            int rowsAffected = jdbcTemplate.update(Queries.SQL_DELETE, id);

            return rowsAffected > 0;

        } catch (Exception e) {
            throw new DbOperationException("Delete Role exception");
        }
    }

    private interface Queries {
        String SQL_DELETE = "delete from    Roles where id = ?";
        String SQL_CREATE = "insert into Roles (id,name,created_date,updated_date) values (?,?,?,?)";
        String SQL_UPDATE = "update Roles set name = ?, updated_date = ? where id = ?";
        String SQL_GET_BY_ID = "select * from Roles where id = ?";
    }

    private static class CurrentDate {
        private static Date getCurDate() {
            return new Date(System.currentTimeMillis());
        }
    }
}
