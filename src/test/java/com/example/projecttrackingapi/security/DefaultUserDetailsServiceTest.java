package com.example.projecttrackingapi.security;

import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserDetailsServiceTest {

    @InjectMocks
    private DefaultUserDetailsService defaultUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_WhenUserExists_ReturnsUser() {
        final var expectedUser = new org.springframework.security.core.userdetails.User("user1", "123456", new ArrayList<>());
        final var userEntity = new User(1L, "user1", "User", "Test", "123456");

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(userEntity));

        final var result = defaultUserDetailsService.loadUserByUsername("user1");

        assertEquals(expectedUser, result);
    }

    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ThrowsException() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> defaultUserDetailsService.loadUserByUsername("user1"));
    }
}