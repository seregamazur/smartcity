package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionDaoImplTest extends BaseTest {

    private Transaction transaction = new Transaction(1L, 1L,
            5000L, 3000L,
            LocalDateTime.now(), LocalDateTime.now());
    private static TransactionDaoImpl transDao;

    @BeforeAll
    public static void start() {
        setup();
        transDao = new TransactionDaoImpl(dataSource);
    }

    @Test
    public void testCreateTransaction() {
        assertThat(transDao.create(transaction))
                .isEqualToIgnoringGivenFields(transaction,
                        "createdDate", "updatedDate");
    }

    @Test
    public void testCreateTransaction_invalidTaskId() {
        transaction.setTaskId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> transDao.create(transaction));
    }

    @Test
    public void testCreateTransaction_missingTaskId() {
        transaction.setTaskId(null);
        assertThrows(DbOperationException.class, () -> transDao.create(transaction));
    }

    @Test
    public void testGetTransaction() {
        transDao.create(transaction);
        assertThat(transaction).
                isEqualToIgnoringGivenFields(transDao.get(1L),
                        "createdDate", "updatedDate");
    }

    @Test
    public void testGetTransaction_invalidId() {
        assertThrows(DbOperationException.class, () -> transDao.get(null));
        assertThrows(DbOperationException.class, () -> transDao.get(Long.MAX_VALUE));
    }

    @Test
    public void testUpdateTransaction() {
        transDao.create(transaction);
        Transaction updatedTransaction = new Transaction(1L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        transDao.update(updatedTransaction);
        assertThat(transDao.get(1L)).isEqualToIgnoringGivenFields(updatedTransaction,
                "createdDate", "updatedDate");
    }

    @Test
    public void testUpdateTransaction_invalidId() {
        Transaction newTransaction = new Transaction(500L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        assertThrows(NotFoundException.class, () -> transDao.update(newTransaction));
    }

    @Test
    public void testDeleteTransaction() {
        transDao.create(transaction);
        assertTrue(transDao.delete(1L));
    }

    @Test
    public void testDeleteTransaction_invalidId() {
        assertThrows(NotFoundException.class, () -> transDao.delete(Long.MAX_VALUE));
    }

    @AfterAll
    public static void cleanUp() {
        tearDown();
    }

    @AfterEach
    public void cleanTransactions() {
        template.update("set global foreign_key_checks=0");
        template.update("DELETE FROM Transactions");
        template.update("set global foreign_key_checks=1");
        template.update("ALTER TABLE Transactions AUTO_INCREMENT = 1");
        //everybody will made same method with clearing and autoincrementing their data
    }
}