package com.smartcity.dao;

import com.smartcity.domain.Budget;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.BudgetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class BudgetDaoImpl implements BudgetDao {

    private static final Logger logger = LoggerFactory.getLogger(BudgetDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public BudgetDaoImpl(DataSource source) {
        template = new JdbcTemplate(source);
    }

    public Budget createOrUpdate(Budget budget) {

        //check if a record exists in Budget
        int amount;
        int affected;
        try {
            amount = template.queryForObject(Queries.SQL_TRANSACTION_COUNT, Integer.class);
        } catch (Exception e) {
            String err = "Failed checking if Budget exists in DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }

        //if a record already exists in DB, update it
        if (amount > 0) {

            try {
                affected = template.update(Queries.SQL_TRANSACTION_UPDATE,
                        budget.getValue());
            } catch (Exception e) {
                String err = "Failed updating Budget in DB: " + e.getMessage();
                logger.error(err);
                throw new DbOperationException(err);
            }

            if (affected < 1) {
                throw new DbOperationException("Could not update Budget(updated 0 entries)");
            }

            return budget;
        }

        //otherwise, create a new record
        try {
            logger.warn("No budget entry found in DB; creating a new entry!");
            affected = template.update(Queries.SQL_TRANSACTION_CREATE,
                    budget.getValue());
        } catch (
                Exception e) {
            String err = "Failed inserting Budget into DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }

        if (affected < 1) {
            throw new DbOperationException("Could not create Budget(created 0 entries)");
        }

        return budget;

    }

    public Budget get() {
        try {
            return template.queryForObject(Queries.SQL_TRANSACTION_GET, BudgetMapper.getInstance());
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            String err = "Budget not found in DB: " + e.getMessage();
            logger.error(err);
            throw new NotFoundException(err);
        } catch (Exception e) {
            String err = "Failed selecting Budget from DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }
    }

    class Queries {
        static final String SQL_TRANSACTION_CREATE = "INSERT INTO Budget(value) VALUES(?)";
        static final String SQL_TRANSACTION_GET = "SELECT * FROM Budget";
        static final String SQL_TRANSACTION_UPDATE = "UPDATE Budget SET value = ?";
        static final String SQL_TRANSACTION_COUNT = "SELECT count(*) FROM Budget";
    }
}
