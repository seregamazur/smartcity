package com.smartcity.dao;

import com.smartcity.domain.Task;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskDaoImplTest extends BaseTest {

    private Task task = new Task(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);
    private static TaskDao taskDao;

    @BeforeAll
    public static void setUpDataSource() {
        setup();
        taskDao = new TaskDaoImpl(dataSource);
    }

    @Test
    public void testCreateTask() {
        assertEquals(task, taskDao.create(task));
    }

    @Test
    public void testCreateTask_InvalidUsersOrganizationsId() {
        task.setUsersOrganizationsId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> taskDao.create(task));
    }

    @Test
    public void testCreateTask_MissedUsersOrganizationsId() {
        task.setUsersOrganizationsId(null);
        assertThrows(DbOperationException.class, () -> taskDao.create(task));
    }

    @Test
    public void testGetTaskById() {
        taskDao.create(task);
        Task resultTask = taskDao.get(task.getId());
        assertThat(task).isEqualToIgnoringGivenFields(resultTask,
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    public void testGetTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskDao.get(Long.MAX_VALUE));
    }

    @Test
    public void testGetTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskDao.get(null));
    }

    @Test
    public void testUpdateTask() {
        taskDao.create(task);

        Task updatedTask = new Task(2L, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);

        taskDao.update(updatedTask);

        Task resultTask = taskDao.get(updatedTask.getId());

        assertThat(updatedTask).isEqualToIgnoringGivenFields(resultTask,
                "deadlineDate", "createdAt",
                "updatedAt", "transactionList");
    }

    @Test
    public void testUpdateTask_InvalidId() {
        Task updatedTask = new Task(Long.MAX_VALUE, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskDao.update(updatedTask));
    }

    @Test
    public void testUpdateTask_NullId() {
        Task updatedTask = new Task(null, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskDao.update(updatedTask));
    }


    @Test
    public void testDeleteTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskDao.delete(Long.MAX_VALUE));
    }


    @Test
    public void testDeleteTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskDao.delete(null));
    }

    @Test
    public void testDeleteTask() {
        taskDao.create(task);
        assertTrue(taskDao.delete(task.getId()));
    }

    @AfterEach
    public void close() {
        template.update("DELETE FROM Tasks WHERE id = 2");
        template.update("ALTER TABLE Tasks AUTO_INCREMENT = 2");
    }

    @AfterAll
    public static void tearDownAll() {
        tearDown();
    }
}