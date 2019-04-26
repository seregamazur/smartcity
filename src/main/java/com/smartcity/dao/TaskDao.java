package com.smartcity.dao;

import com.smartcity.domain.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao {

    Task save(Task task);

    Task findById(Integer id);

    boolean deleteById(Integer id);
}
