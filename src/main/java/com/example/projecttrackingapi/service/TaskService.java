package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    void createNewTask(NewTaskRequest request);

    Optional<TaskDto> findById(Long id);

    List<TaskDto> findAll(int page, int size);
}
