package com.smartcity.dao;

import com.smartcity.domain.Budget;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetDao {

    Budget create(Budget budget);

    Budget get(long id);

    Budget update(Budget budget);

    boolean delete(long id);

}
