package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.AbstractProjectTrackingApiApplicationTest;
import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.dto.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class TaskControllerTest extends AbstractProjectTrackingApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createTask_whenItsAValidRequest_Returns201() {
        var task = new NewTaskRequest("Sample Task", "Description", 3, "Low", "ToDo");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(task)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void findTaskById_whenTaskExists_Returns200() {
        mockMvc.perform(get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void findTaskById_whenTaskDoesNotExist_Returns404() {
        mockMvc.perform(get("/tasks/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void findAll_Returns200() {
        mockMvc.perform(get("/tasks?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void updateTask_whenTaskExists_returns200() {
        var task = new TaskDto(1L, "Sample Task Updated", "Description Updated", 3, "Low", "ToDo", 1L);

        mockMvc.perform(put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(task)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void updateTask_whenTaskDoesNotExist_returns404() {
        var task = new TaskDto(1000L, "Sample Task Updated", "Description Updated", 3, "Low", "ToDo", 1L);

        mockMvc.perform(put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(task)))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    private String toJson(Object object) {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}