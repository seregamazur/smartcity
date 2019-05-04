package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionDao {

    Optional<Transaction> create(Transaction transaction);

    Optional<Transaction> get(long id);

    Optional<Transaction> update(Transaction transaction);

    boolean delete(long id);
}
