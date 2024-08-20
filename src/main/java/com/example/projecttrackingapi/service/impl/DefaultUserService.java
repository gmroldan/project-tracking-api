package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.CredentialsDto;
import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.exception.UserPassCombinationException;
import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import com.example.projecttrackingapi.service.AuthenticationService;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class DefaultUserService implements UserService, AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDto> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest)
                .map(this::toDto);
    }

    @Override
    public List<UserDto> findByProject(final Long projectId) {
        return userRepository.findByProjectId(projectId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }

    @Override
    public UserDto login(final CredentialsDto credentials) {
        return userRepository.findByUsername(credentials.user())
                .filter(u -> passwordEncoder.matches(credentials.password(), u.getPassword()))
                .map(this::toDto)
                .orElseThrow(() -> new UserPassCombinationException());
    }
}
