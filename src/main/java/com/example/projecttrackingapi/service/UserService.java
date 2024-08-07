package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<UserDto> findAll(int page, int size);

    List<UserDto> findByProject(Long id);
}
