package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll(int page, int size);
}
