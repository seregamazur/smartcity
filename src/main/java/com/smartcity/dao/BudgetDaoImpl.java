package com.smartcity.dao;

import com.smartcity.domain.Budget;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.RecordExistsException;
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

    public Budget create(Budget budget) {
        int amount;
        try {
            amount = template.queryForObject(Queries.SQL_TRANSACTION_COUNT, Integer.class);
        } catch (Exception e) {
            String err = "Failed checking if Budget exists in DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }
        if (amount > 0) {
            String err = "Budget already exists in DB.";
            logger.error(err);
            throw new RecordExistsException(err);
        }

        try {
            int affected = template.update(Queries.SQL_TRANSACTION_CREATE,
                    budget.getValue());
            if (affected < 1)
                throw new DbOperationException("Could not create Budget");
            return budget;
        } catch (Exception e) {
            String err = "Failed inserting Budget into DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }

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

    public Budget update(Budget budget) {
        try {
            get();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            String err = "Failed checking Budget exists: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }

        try {
            int affected = template.update(Queries.SQL_TRANSACTION_UPDATE,
                    budget.getValue());
            if (affected < 1)
                throw new DbOperationException("Could not create Budget");
            return budget;
        } catch (Exception e) {
            String err = "Failed updating Budget in DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }
    }

    public boolean delete() {
        try {
            int affected = template.update(Queries.SQL_TRANSACTION_DELETE);
            return affected > 0;
        } catch (Exception e) {
            String err = "Failed removing Budget from DB: " + e.getMessage();
            logger.error(err);
            throw new DbOperationException(err);
        }
    }

    class Queries {
        static final String SQL_TRANSACTION_CREATE = "INSERT INTO Budget(id,value) VALUES(1,?)";
        static final String SQL_TRANSACTION_GET = "SELECT * FROM Budget";
        static final String SQL_TRANSACTION_UPDATE = "UPDATE Budget SET value = ?";
        static final String SQL_TRANSACTION_DELETE = "DELETE FROM Budget";
        static final String SQL_TRANSACTION_COUNT = "SELECT count(*) FROM Budget";
    }
}
