package com.smartcity.mapper;

import com.smartcity.domain.Role;
import com.smartcity.exceptions.NotFoundException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RoleMapper implements RowMapper<Role> {

    private static final RoleMapper instance = new RoleMapper();

    private RoleMapper() {
    }

    public static RoleMapper getInstance() {
        return instance;
    }

    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        if (resultSet != null) {
            Role role = new Role();
            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
            role.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
            role.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
            return role;
        } else throw new NotFoundException("Can't get role. ResultSet is null!");
    }
}
