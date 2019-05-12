package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertThrows(NotFoundException.class, () -> transDao.get(null));
        assertThrows(NotFoundException.class, () -> transDao.get(Long.MAX_VALUE));
    }

    @Test
    public void testGetTransactionByTaskId() {
        transDao.create(transaction);
        assertThat(transaction).isEqualToIgnoringGivenFields(transDao.getByTaskId(1L).get(0),
                "createdDate", "updatedDate");
    }

    @Test
    public void testGetTransactionByTaskId_amountOfTransactions() {
        List<Transaction> list = new ArrayList<>();
        for(int i = 1;i<4;i++){
            transaction.setId((long) i);
            transDao.create(transaction);
            list.add(transaction);
            assertThat(list.get(i-1)).isEqualToIgnoringGivenFields(transDao.getByTaskId(1L).get(i-1),
                    "createdDate","updatedDate");
        }
    }

    @Test
    public void testGetTransactionByTaskId_nullList() {
      assertThrows(NotFoundException.class, () -> transDao.getByTaskId(1L));
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
        clearTables("Transactions");
    }
}