package com.smartcity.mapper;

import com.smartcity.domain.Budget;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetMapper implements RowMapper<Budget> {

    private static final BudgetMapper instance = new BudgetMapper();

    public static BudgetMapper getInstance() {
        return instance;
    }

    private BudgetMapper() {

    }

    public Budget mapRow(ResultSet resultSet, int i) throws SQLException {

        Budget budget = new Budget();
        budget.setValue(resultSet.getLong("value"));

        return budget;

    }
}
