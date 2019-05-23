package com.smartcity.service;

import com.smartcity.dao.BaseTest;
import com.smartcity.dto.TransactionDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceImplTest extends BaseTest {

    private final TransactionDto transaction = new TransactionDto(2L, 1L,
            5000L, 3000L,
            LocalDateTime.now(), LocalDateTime.now());

    @Autowired
    private TransactionService service;

    @Test
    public void testCreateTransactionDto() {
        assertThat(service.create(transaction))
                .isEqualToIgnoringGivenFields(transaction, "id",
                        "createdDate", "updatedDate");
    }

    @Test
    public void testCreateTransactionDto_invalidTaskId() {
        transaction.setTaskId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> service.create(transaction));
    }

    @Test
    public void testCreateTransactionDto_missingTaskId() {
        transaction.setTaskId(null);
        assertThrows(DbOperationException.class, () -> service.create(transaction));
    }

    @Test
    public void testFindTransactionDto() {
        TransactionDto dto = service.create(transaction);
        assertThat(transaction).
                isEqualToIgnoringGivenFields(service.findById(dto.getId()),
                        "id", "createdDate", "updatedDate");
    }

    @Test
    public void testFindTransactionDto_invalidId() {
        assertThrows(NotFoundException.class, () -> service.findById(null));
        assertThrows(NotFoundException.class, () -> service.findById(Long.MAX_VALUE));
    }

    @Test
    public void testFindTransactionDtoByTaskId() {
        service.create(transaction);
        assertThat(transaction).isEqualToIgnoringGivenFields(
                service.findByTaskId(transaction.getTaskId()).get(0), "id",
                "createdDate", "updatedDate");
    }

    @Test
    public void testFindTransactionDtoByTaskId_amountOfTransactions() {
        List<TransactionDto> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            transaction.setId((long) i);
            service.create(transaction);
            list.add(transaction);
            assertThat(list.get(i - 1)).isEqualToIgnoringGivenFields(
                    service.findByTaskId(transaction.getTaskId()).get(i - 1),
                    "createdDate", "updatedDate");
        }
    }

    @Test
    public void testFindTransactionDtoByTaskId_emptyList() {
        assertThat(new ArrayList()).isEqualTo(service.findByTaskId(Long.MAX_VALUE));
    }

    @Test
    public void testUpdateTransactionDto() {
        service.create(transaction);
        TransactionDto updatedTransactionDto = new TransactionDto(1L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        service.update(updatedTransactionDto);
        assertThat(service.findById(updatedTransactionDto.getId()))
                .isEqualToIgnoringGivenFields(updatedTransactionDto,
                        "createdDate", "updatedDate");
    }

    @Test
    public void testUpdateTransactionDto_invalidId() {
        TransactionDto newTransaction = new TransactionDto(500L, 1L,
                800000L, 44000L,
                LocalDateTime.now(), LocalDateTime.now());
        assertThrows(NotFoundException.class, () -> service.update(newTransaction));
    }

    @Test
    public void testDeleteTransactionDto() {
        service.create(transaction);
        assertTrue(service.delete(1L));
    }

    @Test
    public void testDeleteTransactionDto_invalidId() {
        assertThrows(NotFoundException.class, () -> service.delete(Long.MAX_VALUE));
    }

    @AfterEach
    public void cleanTransactions() {
        clearTables("Transactions");
    }
}