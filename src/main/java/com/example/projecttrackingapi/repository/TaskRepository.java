package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findBySprintId(Long sprintId);

}
