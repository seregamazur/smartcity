package com.smartcity.mapper;

import com.smartcity.domain.Organization;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationMapper implements RowMapper<Organization> {

    public Organization mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
