package com.example.projecttrackingapi.exception;

public class TaskNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Task not found with ID: ";

    public TaskNotFoundException(Long taskId) {
        super(DEFAULT_MESSAGE + taskId);
    }
}
