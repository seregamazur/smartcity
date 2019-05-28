package com.smartcity.service;

import com.smartcity.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto create(TaskDto task);

    TaskDto findById(Long id);

    List<TaskDto> findByOrganizationId(Long id);

    List<TaskDto> findByUserId(Long id);

    TaskDto update(TaskDto task);

    boolean delete(Long id);
}
