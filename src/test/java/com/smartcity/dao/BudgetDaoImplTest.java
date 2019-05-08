package com.smartcity.dao;

import com.smartcity.domain.Budget;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.RecordExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetDaoImplTest extends BaseTest {

    private BudgetDaoImpl budgetDao;
    private Budget budget = new Budget(10000L);

    @BeforeEach
    public void setup() {
        super.setup();
        //dataSource.setUrl("jdbc:mysql://localhost:3306/smartcity");
        budgetDao = new BudgetDaoImpl(dataSource);
    }

    @Test
    public void testCreateBudget() {
        assertEquals(budgetDao.create(budget), budget);
    }

    @Test
    public void testCreateBudget_alreadyExists() {
        budgetDao.create(budget);
        assertThrows(RecordExistsException.class, () -> budgetDao.create(budget));
    }

    @Test
    public void testGetBudget() {
        budgetDao.create(budget);

        Budget created = budgetDao.get();

        assertEquals(created, budget);
    }

    @Test
    public void testGetBudget_noRowsFound() {
        assertThrows(NotFoundException.class, () -> budgetDao.get());
    }

    @Test
    public void testUpdateBudget() {
        budgetDao.create(budget);

        budget.setValue(20000L);
        budgetDao.update(budget);

        Budget updated = budgetDao.get();

        assertEquals(updated, budget);
    }

    @Test
    public void testUpdateBudget_noRowsFound() {
        assertThrows(NotFoundException.class, () -> budgetDao.update(budget));
    }

    @Test
    public void testDeleteBudget() {
        budgetDao.create(budget);

        assertTrue(budgetDao.delete());
    }

    @Test
    public void testDeleteBudget_noRowsFound() {
        assertFalse(budgetDao.delete());
    }

    @AfterEach
    public void tearDown() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("DELETE FROM Budget");
    }
}