package com.smartcity.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartcity.domain.Budget;
import com.smartcity.exceptions.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BudgetDaoImplTest extends BaseTest {

    @Autowired
    private BudgetDaoImpl budgetDao;
    private Budget budget = new Budget(10000L);

    @Test
    public void testCreateBudget() {
        assertEquals(budgetDao.createOrUpdate(budget), budget);
    }

    @Test
    public void testGetBudget() {
        budgetDao.createOrUpdate(budget);

        Budget created = budgetDao.get();

        assertEquals(created, budget);
    }

    @Test
    public void testGetBudget_noRowsFound() {
        assertThrows(NotFoundException.class, () -> budgetDao.get());
    }

    @Test
    public void testUpdateBudget() {
        budgetDao.createOrUpdate(budget);

        budget.setValue(20000L);
        budgetDao.createOrUpdate(budget);

        Budget updated = budgetDao.get();

        assertEquals(updated, budget);
    }

    @AfterEach
    public void afterEach() {
        clearTables("Budget");
    }
}