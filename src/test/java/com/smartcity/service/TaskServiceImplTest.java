package com.smartcity.service;

import com.smartcity.dao.BaseTest;
import com.smartcity.domain.Task;
import com.smartcity.dto.TaskDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest extends BaseTest {

    private TaskDto task = new TaskDto(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);

    @Autowired
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        TaskDto result = taskService.create(task);
        assertThat(result).isEqualToIgnoringGivenFields(taskService.findById(result.getId()),
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    public void testCreateTask_InvalidUsersOrganizationsId() {
        task.setUsersOrganizationsId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> taskService.create(task));
    }

    @Test
    public void testCreateTask_MissedUsersOrganizationsId() {
        task.setUsersOrganizationsId(null);
        assertThrows(DbOperationException.class, () -> taskService.create(task));
    }

    @Test
    public void testFindTaskById() {
        TaskDto result = taskService.create(task);
        assertThat(result).isEqualToIgnoringGivenFields(taskService.findById(result.getId()),
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    public void testFindTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskService.findById(Long.MAX_VALUE));
    }

    @Test
    public void testFindTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskService.findById(null));
    }

    @Test
    public void testFindTaskByOrganizationId() {
        taskService.create(task);
        List<TaskDto> resultTaskList = taskService.findByOrganizationId(1L);
        List<TaskDto> expTaskList = new ArrayList<>();
        expTaskList.add(taskService.findById(1L));
        expTaskList.add(task);
        int i = 0;
        for (TaskDto t1 : resultTaskList) {
            assertThat(t1).isEqualToIgnoringGivenFields(expTaskList.get(i), "deadlineDate",
                    "createdAt", "updatedAt");
            i++;
        }
    }

    @Test
    public void testUpdateTask() {
        taskService.create(task);

        TaskDto updatedTask = new TaskDto(2L, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);

        taskService.update(updatedTask);

        TaskDto resultTask = taskService.findById(updatedTask.getId());

        assertThat(updatedTask).isEqualToIgnoringGivenFields(resultTask,
                "deadlineDate", "createdAt",
                "updatedAt", "transactionList");
    }

    @Test
    public void testUpdateTask_InvalidId() {
        TaskDto updatedTask = new TaskDto(Long.MAX_VALUE, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskService.update(updatedTask));
    }

    @Test
    public void testUpdateTask_NullId() {
        TaskDto updatedTask = new TaskDto(null, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskService.update(updatedTask));
    }


    @Test
    public void testDeleteTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskService.delete(Long.MAX_VALUE));
    }


    @Test
    public void testDeleteTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskService.delete(null));
    }

    @Test
    public void testDeleteTask() {
        taskService.create(task);
        assertTrue(taskService.delete(task.getId()));
    }

    @AfterEach
    public void close() {
        clearTables("Transactions");
    }
}