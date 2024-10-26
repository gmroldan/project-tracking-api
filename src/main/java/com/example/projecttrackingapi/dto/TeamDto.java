package com.example.projecttrackingapi.dto;

import java.util.List;

public record TeamDto(Long id, String name, List<TeamMemberDto> members) {
}
