package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.Board;
import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.dto.ProjectDto;
import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.service.ProjectService;
import com.example.projecttrackingapi.service.TaskService;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping("/{id}/team")
    public ResponseEntity<List<UserDto>> findProjectUsers(@PathVariable final Long id) {
        final var users = userService.findByProject(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/board")
    public ResponseEntity<Board> findCurrentSprintBoard(@PathVariable Long id) {
        var tasks = taskService.findBySprintId(id);
        return ResponseEntity.ok().body(new Board(tasks));
    }

    @PostMapping
    public ResponseEntity<Object> createNewProject(@RequestBody NewProjectRequest request) {
        projectService.createNewProject(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> findAllProjects() {
        var projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> findProjectById(@PathVariable Long id) {
        return projectService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
