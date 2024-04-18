package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll(int page, int size);
}
