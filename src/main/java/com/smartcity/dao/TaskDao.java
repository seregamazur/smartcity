package com.smartcity.dao;

import com.smartcity.domain.Task;

public interface TaskDao {

    Task create(Task task);

    Task get(Long id);

    Task update(Task task);

    boolean delete(Long id);
}
