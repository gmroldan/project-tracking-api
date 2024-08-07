package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.Board;
import com.example.projecttrackingapi.service.TaskService;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProjectController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/projects/{id}/team")
    public ResponseEntity findProjectUsers(@PathVariable final Long id) {
        final var users = userService.findByProject(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("projects/{id}/board")
    public ResponseEntity findCurrentSprintBoard(@PathVariable Long id) {
        var tasks = taskService.findBySprintId(id);
        return ResponseEntity.ok().body(new Board(tasks));
    }
}
