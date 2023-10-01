package com.example.projecttrackingapi.dto;

public record TaskDto(Long id, String title, String description, int storyPoints, String priority, String status) {
}
