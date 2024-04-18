package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @InjectMocks
    DefaultUserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void findAll_whenUsersExists_returnsListWithElements() {
        var user1 = new User(1L, "user-test1", "User Test 1");
        var user2 = new User(2L, "user-test2", "User Test 2");
        when(userRepository.findAll(anyInt(), anyInt())).thenReturn(List.of(user1, user2));

        var result = userService.findAll(0, 2);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

}