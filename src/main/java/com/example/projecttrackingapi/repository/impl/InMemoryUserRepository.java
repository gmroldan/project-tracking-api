package com.example.projecttrackingapi.repository.impl;

import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserRepository implements UserRepository {

    private static final Map<Long, User> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put(1L, new User(1L, "user-test1", "User Test 1"));
        USERS.put(2L, new User(2L, "user-test2", "User Test 2"));
        USERS.put(3L, new User(3L, "user-test3", "User Test 3"));
        USERS.put(4L, new User(4L, "user-test4", "User Test 4"));
    }

    @Override
    public List<User> findAll(int page, int size) {
        var startIndex = (page - 1) * size;
        var endIndex = Math.min(startIndex + size, USERS.size());

        if (startIndex >= USERS.size() || startIndex < 0) {
            return List.of();
        }

        var entries = new ArrayList<>(USERS.entrySet());
        var sublist = entries.subList(startIndex, endIndex);

        return sublist.stream()
                .map(Map.Entry::getValue)
                .toList();
    }
}
