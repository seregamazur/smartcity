package com.smartcity.dao;

import com.smartcity.domain.Transaction;

public interface TransactionDao {

    Transaction create(Transaction transaction);

    Transaction get(Long id);

    Transaction update(Transaction transaction);

    boolean delete(Long id);
}
