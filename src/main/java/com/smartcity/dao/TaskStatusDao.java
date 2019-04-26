package com.smartcity.dao;

import com.smartcity.domain.TaskStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusDao {

    TaskStatus save(TaskStatus taskStatus);

    TaskStatus findById(Integer id);

    boolean deleteById(Integer id);
}
