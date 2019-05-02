package com.smartcity.service;

import com.smartcity.domain.Task;
import com.smartcity.dto.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    TaskDto create(TaskDto task);

    TaskDto findById(Long id);

    List<TaskDto> findByOrganizationId(Long id);

    List<TaskDto> findByUserId(Long id);

    TaskDto update(TaskDto task);

    boolean delete(Long id);
}
