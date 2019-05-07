package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            transaction.setCreatedDate(LocalDateTime.now());
            transaction.setUpdatedDate(LocalDateTime.now());
            template.update(con -> {
                        PreparedStatement ps = con.prepareStatement(
                                Queries.SQL_TRANSACTION_CREATE, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, transaction.getTaskId());
                        ps.setLong(2, transaction.getCurrentBudget());
                        ps.setLong(3, transaction.getTransactionBudget());
                        ps.setObject(4, transaction.getCreatedDate());
                        ps.setObject(5, transaction.getUpdatedDate());
                        return ps;
                    },
                    holder
            );
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
            return template.queryForObject(Queries.SQL_TRANSACTION_GET, TransactionMapper.getInstance(),
                    id);
        } catch (Exception e) {
            logger.error("Can't get transaction by id={}. Error: ", id, e);
            throw new DbOperationException("Can't get transaction with id." + id);
        }
    }

    public Transaction update(Transaction transaction) {
        Transaction transactionFromDb;
        try {
            transactionFromDb = this.get(transaction.getId());
        } catch (Exception e) {
            logger.error("Can't get transaction by id={}. Transaction:{}.", transaction.getId(), transaction);
            throw new NotFoundException("Can't get transaction by id=" + transaction.getId() +
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
        try {
            this.get(id);
        } catch (Exception e) {
            logger.error("Can't get transaction by id={}.", id);
            throw new NotFoundException("Can't get transaction by id=" + id);
        }
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
                "transaction_budget = ? , updated_date = ? where id = ?";
        static final String SQL_TRANSACTION_DELETE = "DELETE FROM Transactions where id = ?";
    }

}