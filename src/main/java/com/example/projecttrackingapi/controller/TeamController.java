package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.NewTeamRequest;
import com.example.projecttrackingapi.dto.TeamDto;
import com.example.projecttrackingapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/teams")
    public List<TeamDto> findAllTeams() {
        return teamService.findAllTeams();
    }

    @PostMapping("/teams")
    public ResponseEntity<Object> createNewTeam(@RequestBody NewTeamRequest request) {
        teamService.createNewTeam(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        return teamService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/teams")
    public ResponseEntity<Object> updateTeam(@RequestBody TeamDto request) {
        teamService.updateTeam(request);
        return ResponseEntity.ok().build();
    }
}
