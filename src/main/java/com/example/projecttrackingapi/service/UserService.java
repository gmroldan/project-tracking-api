package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserDto> findAll(int page, int size);
}
