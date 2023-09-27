package com.example.projecttrackingapi.dto;

public record NewTaskRequest(String tittle, String description, int storyPoints, String priority, String status) {
}
