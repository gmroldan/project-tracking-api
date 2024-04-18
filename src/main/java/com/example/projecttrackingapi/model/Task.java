package com.example.projecttrackingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private String description;
    private Integer storyPoints;
    private String priority;
    private String status;
    private Long userIdAssigned;
}
