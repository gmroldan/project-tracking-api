package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.dto.TaskDto;
import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void findById_whenTaskExists_ReturnsTask() {
        var persistedTask = new Task(1L, "Sample Task", "Description", 3, "Low", "ToDo");
        var expectedTask = new TaskDto(1L, "Sample Task", "Description", 3, "Low", "ToDo");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(persistedTask));

        var result = taskService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(expectedTask, result.get());
    }

    @Test
    void findById_whenTaskDoesNotExist_ReturnsEmptyOptional() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        var result = taskService.findById(1L);

        assertTrue(result.isEmpty());
    }
}