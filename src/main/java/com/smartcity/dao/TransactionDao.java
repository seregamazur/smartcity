package com.smartcity.dao;

import com.smartcity.domain.Transaction;

import java.util.List;

public interface TransactionDao {

    Transaction create(Transaction transaction);

    Transaction findById(Long id);

    Transaction update(Transaction transaction);

    boolean delete(Long id);

    List<Transaction> findByTaskId(Long id);

}
