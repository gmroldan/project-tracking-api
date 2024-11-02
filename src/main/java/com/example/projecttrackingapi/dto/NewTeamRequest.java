package com.example.projecttrackingapi.dto;

import java.util.List;

public record NewTeamRequest(String name, List<TeamMemberDto> members) {
}
