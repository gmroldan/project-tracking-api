package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
