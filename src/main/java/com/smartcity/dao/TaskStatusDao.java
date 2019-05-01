package com.smartcity.dao;

import com.smartcity.domain.TaskStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusDao {

    TaskStatus create(TaskStatus taskStatus);

    TaskStatus get(long id);

    TaskStatus update(TaskStatus taskStatus);

    boolean delete(long id);
}
