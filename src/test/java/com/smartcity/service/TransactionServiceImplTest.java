package com.smartcity.service;

import com.smartcity.dao.TransactionDao;
import com.smartcity.domain.Transaction;
import com.smartcity.dto.TransactionDto;
import com.smartcity.mapperDto.TransactionDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    private final TransactionDto transactionDto = new TransactionDto(2L, 1L,
            5000L, 3000L,
            LocalDateTime.now(), LocalDateTime.now());

    private TransactionDtoMapper transDtoMapper = new TransactionDtoMapper();

    @Mock
    private TransactionDao transDao;
    @InjectMocks
    private TransactionServiceImpl transService;

    private Transaction transaction;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        transService = new TransactionServiceImpl(transDao, transDtoMapper);
        transaction = transDtoMapper.transactionDtoToTransaction(transactionDto);
    }

    @Test
    public void testCreateTransactionDto() {
        doReturn(transaction).when(transDao).create(transaction);
        assertThat(transService.create(transactionDto))
                .isEqualToIgnoringGivenFields(transDtoMapper.transactionToTransactionDto(transaction), "id",
                        "createdDate", "updatedDate");
    }

    @Test
    public void testFindTransactionDto() {
        doReturn(transaction).when(transDao).findById(transaction.getId());
        assertThat(transService.findById(transactionDto.getId()))
                .isEqualToIgnoringGivenFields(transDtoMapper.transactionToTransactionDto(transaction), "id",
                        "createdDate", "updatedDate");
    }

    @Test
    public void testFindTransactionsDtoByTaskId() {
        List<Transaction> transList = Collections.singletonList(transaction);
        List<TransactionDto> transDtoList = Collections.singletonList(
                transDtoMapper.transactionToTransactionDto(transaction));
        doReturn(transList).when(transDao).findByTaskId(transaction.getTaskId());
        assertEquals(transDtoList, transService.findByTaskId(transactionDto.getTaskId()));
    }

    @Test
    public void testFindTransactionsDtoByTaskId_emptyList() {
        assertThat(Collections.emptyList()).isEqualTo(transService.findByTaskId(Long.MAX_VALUE));
    }

    @Test
    public void testUpdateTransactionDto() {
        doReturn(transaction).when(transDao).update(transaction);
        assertThat(transService.update(transactionDto)).isEqualToIgnoringGivenFields(
                transDtoMapper.transactionToTransactionDto(transaction),
                "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteTransactionDto() {
        doReturn(true).when(transDao).delete(transaction.getId());
        assertTrue(transService.delete(transactionDto.getId()));
    }

}