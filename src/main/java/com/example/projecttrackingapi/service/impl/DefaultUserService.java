package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import com.example.projecttrackingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        return StreamSupport.stream(userRepository.findAll(pageRequest).spliterator(), false)
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName());
    }

}
