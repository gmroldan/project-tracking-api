package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.TeamDto;
import com.example.projecttrackingapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
