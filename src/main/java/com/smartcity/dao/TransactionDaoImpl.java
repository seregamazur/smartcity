package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

public class TransactionDaoImpl implements TransactionDao {
    private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public TransactionDaoImpl(DataSource source) {
        template = new JdbcTemplate(source);
    }

    public Optional<Transaction> create(Transaction transaction) {
        try {
            template.update(Queries.SQL_TRANSACTION_CREATE,
                    transaction.getTaskId(),
                    transaction.getCurrentBudget(),
                    transaction.getTransactionBudget(),
                    LocalDateTime.now(),
                    transaction.getUpdatedDate());
            return Optional.of(transaction);
        } catch (Exception e) {
            logger.error("Create transaction error:" + e.getMessage());
            throw new DbOperationException("Create transaction error");
        }
    }

    public Optional<Transaction> get(long id) {
        try {
            return Optional.ofNullable(template.queryForObject(Queries.SQL_TRANSACTION_GET, TransactionMapper.getInstance(),
                    (Long) id));

        } catch (Exception e) {
            logger.error("Get transaction error:" + e.getMessage());
            throw new DbOperationException("Get transaction error");
        }
    }

    public Optional<Transaction> update(Transaction transaction) {
        try {
            template.update(Queries.SQL_TRANSACTION_UPDATE,
                    transaction.getTaskId(),
                    transaction.getCurrentBudget(),
                    transaction.getTransactionBudget(),
                    LocalDateTime.now(),
                    transaction.getUpdatedDate(),
                    transaction.getId());
            return Optional.of(transaction);
        } catch (Exception e) {
            logger.error("Update transaction error:" + e.getMessage());
            throw new DbOperationException("Update transaction error");
        }
    }

    public boolean delete(long id) {
        try {
            int rowsAffected = template.update(Queries.SQL_TRANSACTION_DELETE, id);
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.error("Delete transaction error:" + e.getMessage());
            throw new DbOperationException("Delete transaction error");
        }
    }

    class Queries {
        static final String SQL_TRANSACTION_CREATE = "INSERT INTO Transactions(task_id,current_budget,transaction_budget," +
                "created_date,updated_date) values(?,?,?,?,?)";
        static final String SQL_TRANSACTION_GET = "SELECT * FROM Transactions where id = ?";
        static final String SQL_TRANSACTION_UPDATE = "UPDATE Transactions set task_id = ? , current_budget = ? ," +
                "transaction_budget = ? ,created_date = ? , updated_date = ? where id = ?";
        static final String SQL_TRANSACTION_DELETE = "DELETE FROM Transactions where id = ?";
    }

}