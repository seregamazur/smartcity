package com.smartcity.dao;

import com.smartcity.domain.Budget;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.RecordExistsException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetDaoImplTest extends BaseTest {

    private static BudgetDaoImpl budgetDao;
    private Budget budget = new Budget(10000L);

    @BeforeAll
    public static void setupBudgetTests() {
        setup();
        //dataSource.setUrl("jdbc:mysql://localhost:3306/mydb?useSSL=false");
        budgetDao = new BudgetDaoImpl(dataSource);
    }

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


    @AfterAll
    public static void afterAll(){
        tearDown();
    }

    @AfterEach
    public void afterEach() {
        clearTables("Budget");
    }
}