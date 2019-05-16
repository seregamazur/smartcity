package com.smartcity.dao;

import com.smartcity.domain.Budget;

public interface BudgetDao {

    Budget createOrUpdate(Budget budget);

    Budget get();

}
