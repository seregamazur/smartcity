package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionDaoImpl implements TransactionDao {
    private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public TransactionDaoImpl(DataSource source) {
        template = new JdbcTemplate(source);
    }

    public Transaction create(Transaction transaction) {
        try {
            LocalDateTime currDate = LocalDateTime.now();
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            transaction.setCreatedDate(currDate);
            transaction.setUpdatedDate(currDate);
            template.update(con -> createStatement(transaction, con), holder);
            transaction.setId(Objects.requireNonNull(holder.getKey()).longValue());
            return transaction;
        } catch (Exception e) {
            logger.error("Can't create transaction:{}. Error:{}", transaction, e.getMessage());
            throw new DbOperationException("Can't create transaction by id=" + transaction.getId() +
                    "Transaction:" + transaction);
        }
    }

    public Transaction get(Long id) {
        try {
            return template.queryForObject(Queries.SQL_TRANSACTION_GET,
                    TransactionMapper.getInstance(), id);
        } catch (EmptyResultDataAccessException erd) {
            throw loggedNotFoundException(id);
        } catch (Exception e) {
            logger.error("Can't get transaction by id={}. Error: ", id, e);
            throw new DbOperationException("Can't get transaction with id." + id);
        }
    }

    public Transaction update(Transaction transaction) {
        Transaction transactionFromDb;
        try {
            transactionFromDb = this.get(transaction.getId());
        } catch (EmptyResultDataAccessException e) {
            logger.error("Can't update transaction by id={}. Transaction:{}.", transaction.getId(), transaction);
            throw new NotFoundException("Can't update transaction by id=" + transaction.getId() +
                    "Transaction:" + transaction);
        }
        try {
            transaction.setCreatedDate(transactionFromDb.getCreatedDate());
            transaction.setUpdatedDate(LocalDateTime.now());
            template.update(Queries.SQL_TRANSACTION_UPDATE,
                    transaction.getTaskId(),
                    transaction.getCurrentBudget(),
                    transaction.getTransactionBudget(),
                    transaction.getUpdatedDate(),
                    transaction.getId());
            return transaction;
        } catch (Exception e) {
            logger.error("Update transaction error:" + transaction + " " + e.getMessage());
            throw new DbOperationException("Update transaction error");
        }
    }

    public boolean delete(Long id) {
        int rowsAffected;
        try {
            rowsAffected = template.update(Queries.SQL_TRANSACTION_DELETE, id);
        } catch (Exception e) {
            logger.error("Delete transaction by id = {} error: {}", id, e.getMessage());
            throw new DbOperationException("Delete transaction error");
        }
        if (rowsAffected < 1) {
            throw loggedNotFoundException(id);
        } else return true;
    }

    private PreparedStatement createStatement(Transaction transaction, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                Queries.SQL_TRANSACTION_CREATE, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, transaction.getTaskId());
        ps.setLong(2, transaction.getCurrentBudget());
        ps.setLong(3, transaction.getTransactionBudget());
        ps.setObject(4, transaction.getCreatedDate());
        ps.setObject(5, transaction.getUpdatedDate());
        return ps;
    }

    private NotFoundException loggedNotFoundException(Long id) {
        NotFoundException notFoundException = new NotFoundException("Transaction not found.Id = " + id);
        logger.error("Runtime exception. Transaction by id = {} not found. Message: {}",
                id, notFoundException.getMessage());
        return notFoundException;
    }

    class Queries {
        static final String SQL_TRANSACTION_CREATE = "INSERT INTO Transactions(task_id,current_budget,transaction_budget," +
                "created_date,updated_date) values(?,?,?,?,?)";
        static final String SQL_TRANSACTION_GET = "SELECT * FROM Transactions where id = ?";
        static final String SQL_TRANSACTION_UPDATE = "UPDATE Transactions set task_id = ? , current_budget = ? ," +
                "transaction_budget = ? , updated_date = ? where id = ?";
        static final String SQL_TRANSACTION_DELETE = "DELETE FROM Transactions where id = ?";
    }

}