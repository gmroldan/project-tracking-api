package com.example.projecttrackingapi.dto;

public record TaskDto(Long id, String tittle, String description, int storyPoints, String priority, String status) {
}
