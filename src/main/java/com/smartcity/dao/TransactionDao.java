package com.smartcity.dao;

import com.smartcity.domain.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao {

    Transaction save(Transaction transaction);

    Transaction findById(Integer id);

    boolean deleteById(Integer id);
}
