package com.smartcity.mappers;

import com.smartcity.domain.TaskStatus;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskStatusMapper implements RowMapper<TaskStatus> {

    public TaskStatus mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
