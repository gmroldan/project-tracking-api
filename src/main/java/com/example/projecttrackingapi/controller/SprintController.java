package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.Board;
import com.example.projecttrackingapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class SprintController {

    private final TaskService taskService;

    @GetMapping("sprints/{id}/board")
    public ResponseEntity findSprintTasks(@PathVariable Long id) {
        var tasks = taskService.findBySprintId(id);
        return ResponseEntity.ok().body(new Board(tasks));
    }
}
