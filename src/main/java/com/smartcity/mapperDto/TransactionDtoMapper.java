package com.smartcity.mapperDto;

import com.smartcity.domain.Transaction;
import com.smartcity.dto.TransactionDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoMapper {

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTaskId(transaction.getTaskId());
        transactionDto.setCurrentBudget(transaction.getCurrentBudget());
        transactionDto.setTransactionBudget(transaction.getTransactionBudget());
        transactionDto.setCreatedDate(transaction.getCreatedDate());
        transactionDto.setUpdatedDate(transaction.getUpdatedDate());
        return transactionDto;
    }

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setTaskId(transactionDto.getTaskId());
        transaction.setCurrentBudget(transactionDto.getCurrentBudget());
        transaction.setTransactionBudget(transactionDto.getTransactionBudget());
        transaction.setCreatedDate(transactionDto.getCreatedDate());
        transaction.setUpdatedDate(transactionDto.getUpdatedDate());
        return transaction;
    }
}
