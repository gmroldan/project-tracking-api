package com.example.projecttrackingapi.dto;

public record NewTaskRequest(String title, String description, int storyPoints, String priority, String status) {
}
