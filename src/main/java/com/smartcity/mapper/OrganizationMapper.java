package com.smartcity.mapper;

import com.smartcity.domain.Organization;
import com.smartcity.exceptions.NotFoundException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrganizationMapper implements RowMapper<Organization> {

    private static final OrganizationMapper instance = new OrganizationMapper();

    public static OrganizationMapper getInstance() {
        return instance;
    }

    public Organization mapRow(ResultSet resultSet, int i) throws SQLException {
        if (resultSet == null) {
            throw new NotFoundException("ResultSet is null");
        }
        Organization organization = new Organization();
        organization.setId(resultSet.getLong("id"));
        organization.setName(resultSet.getString("name"));
        organization.setAddress(resultSet.getString("address"));
        organization.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        organization.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return organization;
    }
}
