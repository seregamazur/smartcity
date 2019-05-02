package com.smartcity.mappers;

import com.smartcity.domain.Task;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper  implements RowMapper<Task> {

    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
