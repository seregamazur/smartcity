package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao {

    Transaction create(Transaction transaction);

    Transaction get(Long id);

    Transaction update(Transaction transaction);

    boolean delete(Long id);

    List<Transaction> getByTaskId(Long id);
}
