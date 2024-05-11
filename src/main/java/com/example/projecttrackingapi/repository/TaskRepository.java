package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
