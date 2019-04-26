package com.smartcity.dao;

import com.smartcity.domain.Budget;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetDao {

    Budget save(Budget budget);

    Budget findById(Integer id);

    boolean deleteById(Integer id);

}
