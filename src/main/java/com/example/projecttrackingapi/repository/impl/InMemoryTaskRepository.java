package com.example.projecttrackingapi.repository.impl;

import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(TASKS.get(id));
    }

    @Override
    public List<Task> findAll(int page, int size) {
        var startIndex = (page - 1) * size;
        var endIndex = Math.min(startIndex + size, TASKS.size());

        if (startIndex <= 0) {
            return List.of();
        }

        var entries = TASKS.entrySet()
                .stream()
                .sorted()
                .toList();

        return entries.subList(startIndex, endIndex)
                .stream()
                .map(Map.Entry::getValue)
                .toList();
    }
}
