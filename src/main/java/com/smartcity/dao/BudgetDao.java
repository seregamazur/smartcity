package com.smartcity.dao;

import com.smartcity.domain.Budget;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetDao {

    Budget createOrUpdate(Budget budget);

    Budget get();

}
