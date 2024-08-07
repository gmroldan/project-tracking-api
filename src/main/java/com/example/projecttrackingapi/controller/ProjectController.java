package com.example.projecttrackingapi.controller;

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

    @GetMapping("/projects/{id}/team")
    public ResponseEntity findProjectUsers(@PathVariable final Long id) {
        final var users = userService.findByProject(id);
        return ResponseEntity.ok(users);
    }
}
