package com.smartcity.dao;

import com.smartcity.domain.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao {

    Task create(Task task);

    Task get(long id);

    Task update(Task task);

    boolean delete(long id);
}
