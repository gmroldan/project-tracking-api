package com.example.projecttrackingapi.repository.impl;

import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTaskRepository implements TaskRepository {

    private static final Map<Long, Task> TASKS = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        task.setId((long) (TASKS.size() + 1));
        TASKS.put(task.getId(), task);
        return task;
    }
}
