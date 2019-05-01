package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao {

    Transaction create(Transaction transaction);

    Transaction get(long id);

    Transaction update(Transaction transaction);

    boolean delete(long id);
}
