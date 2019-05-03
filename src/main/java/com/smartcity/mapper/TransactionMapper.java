package com.smartcity.mapper;

import com.smartcity.domain.Transaction;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction> {

    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
