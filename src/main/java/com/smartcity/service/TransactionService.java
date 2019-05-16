package com.smartcity.service;

import com.smartcity.domain.Transaction;
import com.smartcity.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    TransactionDto create(TransactionDto transactionDto);

    TransactionDto findById(Long id);

    TransactionDto update(TransactionDto transactionDto);

    boolean delete(Long id);

    List<TransactionDto> findByTaskId(Long id);

}
