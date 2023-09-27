package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewTaskRequest;

public interface TaskService {

    void createNewTask(NewTaskRequest request);
}
