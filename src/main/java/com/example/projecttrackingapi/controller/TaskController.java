package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity createNewTask(@RequestBody NewTaskRequest request) {
        taskService.createNewTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity findTaskById(@PathVariable Long id) {
        var result = taskService.findById(id);
        return result
                .map(task -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tasks")
    public ResponseEntity findAll(@RequestParam int page,
                                  @RequestParam int size) {
        var result = taskService.findAll(page, size);
        return ResponseEntity.ok().build();
    }
}
