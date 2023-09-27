package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultTaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    DefaultTaskService taskService;

    @Test
    void createNewTask_whenNewTaskIsValid_PersistTheNewTask() {
        var newTask = new NewTaskRequest("Sample Task", "Description", 3, "Low", "ToDo");

        taskService.createNewTask(newTask);

        verify(taskRepository, times(1)).save(any(Task.class));
    }
}