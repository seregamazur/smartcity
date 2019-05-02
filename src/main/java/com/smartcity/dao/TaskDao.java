package com.smartcity.dao;

import com.smartcity.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao {

    Task create(Task task);

    Task findById(Long id);

    List<Task> findByOrganizationId(Long id);

    List<Task> findByUserId(Long id);

    List<Task> findAll();

    Task update(Task task);

    boolean delete(Long id);
}
