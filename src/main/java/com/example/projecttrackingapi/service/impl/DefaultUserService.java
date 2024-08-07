package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDto> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest)
                .map(this::toDto);
    }

    @Override
    public List<UserDto> findByProject(final Long id) {
        // TODO - Query repository by projectId or teamId
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName());
    }

}
