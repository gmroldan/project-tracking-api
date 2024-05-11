package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.dto.TaskDto;
import com.example.projecttrackingapi.exception.TaskNotFoundException;
import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
        var persistedTask = new Task(1L, "Sample Task", "Description", 3, "Low", "ToDo", 1L);
        var expectedTask = new TaskDto(1L, "Sample Task", "Description", 3, "Low", "ToDo", 1L);

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

    @Test
    void findAll_whenTaskExists_returnsListWithElements() {
        var task1 = new Task(1L, "Sample Task 1", "Description 1", 3, "Low", "ToDo", 1L);
        var task2 = new Task(2L, "Sample Task 2", "Description 2", 8, "High", "ToDo", 1L);
        //when(taskRepository.findAll(anyInt(), anyInt())).thenReturn(List.of(task1, task2));
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        var result = taskService.findAll(0, 2);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void update_whenTaskExists_updatesRecord() {
        var persistedTask = new Task(1L, "Sample Task", "Description", 3, "Low", "ToDo", 1L);
        var updatedTask = new TaskDto(1L, "Sample Task Updated", "Description Updated", 3, "Low", "ToDo", 1L);

        when(taskRepository.findById(updatedTask.id()))
                .thenReturn(Optional.of(persistedTask));

        taskService.update(updatedTask);

        verify(taskRepository, times(1))
                .save(persistedTask);
    }

    @Test
    void update_whenTaskDoesNotExist_throwsException() {
        var updatedTask = new TaskDto(1L, "Sample Task Updated", "Description Updated", 3, "Low", "ToDo", 1L);

        when(taskRepository.findById(updatedTask.id())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.update(updatedTask));
    }
}