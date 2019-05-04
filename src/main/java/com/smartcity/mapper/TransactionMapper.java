package com.smartcity.mapper;

import com.smartcity.domain.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionMapper implements RowMapper<Transaction> {

    private static final TransactionMapper instance = new TransactionMapper();

    private TransactionMapper() {
    }

    public static TransactionMapper getInstance() {
        return instance;
    }

    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setTaskId(resultSet.getLong("task_id"));
        transaction.setCurrentBudget(resultSet.getLong("current_budget"));
        transaction.setTransactionBudget(resultSet.getLong("transaction_budget"));
        transaction.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        transaction.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return transaction;
    }
}