package com.smartcity.mapper;

import com.smartcity.domain.Role;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        role.setCreatedDate(resultSet.getDate("created_date"));
        role.setUpdatedDate(resultSet.getDate("updated_date"));
        return role;
    }
}
