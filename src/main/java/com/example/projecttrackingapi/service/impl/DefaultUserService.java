package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll(int page, int size) {
        return userRepository.findAll(page, size)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName());
    }

}
