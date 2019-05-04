package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionDaoImplTest extends BaseTest {

    private TransactionDaoImpl transDao;
    private Transaction transaction = new Transaction(1L, 1L,
            12000L, 24000L,
            LocalDateTime.now(), LocalDateTime.now());

    @BeforeEach
    public void setup() {
        super.setup();
        transDao = new TransactionDaoImpl(dataSource);
        addInsertionsTransaction();
    }


    @Test
    public void testCreateTransaction() {
        assertThat(transDao.create(transaction).get())
                .isEqualToIgnoringGivenFields(transaction,
                        "createdDate", "updatedDate");
    }

    @Test
    public void testCreateTransaction_invalidTaskId() {
        transaction.setId(2L);
        transaction.setTaskId(Long.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> transDao.create(transaction).get());
    }

    @Test
    public void testCreateTransaction_missingTaskId() {
        transaction.setId(2L);
        transaction.setTaskId(null);
        assertThrows(RuntimeException.class, () -> transDao.create(transaction).get());
    }

    @Test
    public void testCreateTransaction_missingCreateDate() {
        transaction.setCreatedDate(null);
        transDao.create(transaction);
        assertNotEquals(transDao.get(1).get().getCreatedDate(), null);
    }

    @Test
    public void testGetTransaction() {
        transDao.create(transaction);
        assertThat(transaction).isEqualToIgnoringGivenFields(transDao.get(1L).get(),
                "createdDate", "updatedDate");
    }

    @Test
    public void testGetTransaction_invalidId() {
        transDao.create(transaction);
        assertThrows(RuntimeException.class, () -> transDao.get(Long.MAX_VALUE).get());
    }

    @Test
    public void testUpdateTransaction() {
        transDao.create(transaction);
        Transaction transaction1 = new Transaction(1L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        transDao.update(transaction1);
        assertThat(transDao.get(1).get()).isEqualToIgnoringGivenFields(transaction1,
                "createdDate", "updatedDate");
    }

    @Test
    public void testUpdateTransaction_invalidId() {
        transDao.create(transaction);
        Transaction newTransaction = new Transaction(Long.MAX_VALUE, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        transDao.update(newTransaction);
        assertThat(transDao.get(1).get()).isEqualToIgnoringGivenFields(transaction,
                "createdDate", "updatedDate");
        assertThrows(RuntimeException.class, () -> transDao.get(Long.MAX_VALUE));
    }

    @Test
    public void testDeleteTransaction() {
        transDao.create(transaction);
        transDao.delete(1);
        assertThrows(RuntimeException.class, () -> transDao.get(1).get());
    }

    @Test
    public void testDeleteTransaction_invalidId(){
        transDao.create(transaction);
        assertFalse(transDao.delete(Long.MAX_VALUE));
    }

    private void addInsertionsTransaction() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("INSERT INTO Users() VALUES (1,'santa@sasa','1234','Saaanta','Saatat','09898965456','2019-05-05','2019-05-05');");
        template.update("INSERT INTO Organizations() VALUES (1,'santa','ssassa','2019-05-05','2019-05-05');");
        template.update("INSERT INTO Users_organizations() VALUES (1,1,1,'2019-05-05','2019-05-05');");
        template.update("Insert into Tasks() values (1,'Title','Desc','2019-05-05','Santa',1000,1000,'2019-05-05','2019-05-05',1);");
    }

    @AfterEach
    public void tearDown() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("set global foreign_key_checks=0");
        template.update("DELETE FROM Users");
        template.update("DELETE FROM Organizations");
        template.update("DELETE FROM Users_organizations");
        template.update("DELETE FROM Tasks");
        template.update("DELETE FROM Transactions");
        template.update("set global foreign_key_checks=1");
        template.update("ALTER TABLE Transactions AUTO_INCREMENT = 1");
    }
}