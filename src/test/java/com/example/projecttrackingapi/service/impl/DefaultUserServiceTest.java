package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @InjectMocks
    DefaultUserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void findAllUsingPagination_whenUsersExists_returnsListWithElements() {
        var user1 = new User(1L, "user-test1", "User", "Test 1");
        var user2 = new User(2L, "user-test2", "User", "Test 2");
        var page = new PageImpl<>(List.of(user1, user2));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        var result = userService.findAll(0, 2);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void findByProject_whenUsersExists_returnsListWithElements() {
        var user1 = new User(1L, "user-test1", "User", "Test 1");
        var user2 = new User(2L, "user-test2", "User", "Test 2");

        when(userRepository.findByProjectId(1L)).thenReturn(List.of(user1, user2));

        var result = userService.findByProject(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

}