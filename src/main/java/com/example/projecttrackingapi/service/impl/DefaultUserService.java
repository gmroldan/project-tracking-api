package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDto> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest)
                .map(this::toDto);
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName());
    }

}
