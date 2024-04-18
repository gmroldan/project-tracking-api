package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(int page, int size);
}
