package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.CredentialsDto;
import com.example.projecttrackingapi.dto.UserDto;
import com.example.projecttrackingapi.exception.UserPassCombinationException;
import com.example.projecttrackingapi.model.User;
import com.example.projecttrackingapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @InjectMocks
    DefaultUserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void findAllUsingPagination_whenUsersExists_returnsListWithElements() {
        var user1 = new User(1L, "user-test1", "User", "Test 1", "");
        var user2 = new User(2L, "user-test2", "User", "Test 2", "");
        var page = new PageImpl<>(List.of(user1, user2));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        var result = userService.findAll(0, 2);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void findByProject_whenUsersExists_returnsListWithElements() {
        var user1 = new User(1L, "user-test1", "User", "Test 1", "");
        var user2 = new User(2L, "user-test2", "User", "Test 2", "");

        when(userRepository.findByProjectId(1L)).thenReturn(List.of(user1, user2));

        var result = userService.findByProject(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void login_WhenUserExistsAndPasswordMatches_ReturnsUserDto() {
        var credentials = new CredentialsDto("user-test1", "123456");
        var user1 = new User(1L, "user-test1", "User", "Test", "$2y$10$2JgoVumB5E0KPTCZaihEeeq/HGxcmQlg4l8obZAwpNPSzNbGDZD02");
        var userDto = new UserDto(1L, "user-test1", "User", "Test");

        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(passwordEncoder.matches(credentials.password(), user1.getPassword())).thenReturn(true);

        var result = userService.login(credentials);

        assertEquals(userDto, result);
    }

    @Test
    void login_WhenUserDoesNotExist_ThrowsException() {
        var credentials = new CredentialsDto("user-test1", "123456");

        when(userRepository.findByUsername(credentials.user())).thenReturn(Optional.empty());

        assertThrows(UserPassCombinationException.class, () ->userService.login(credentials));
    }

    @Test
    void login_WhenUserExistsAndPasswordDoesNotMatch_ThrowsException() {
        var credentials = new CredentialsDto("user-test1", "123456");
        var user1 = new User(1L, "user-test1", "User", "Test", "$2y$10$ORsFWQt/jvKLF4cKk6WJw.fc1iVYNwnD3Q9Qqzfut4a0SZ1HG.HA.");

        when(userRepository.findByUsername(credentials.user())).thenReturn(Optional.of(user1));
        when(passwordEncoder.matches(credentials.password(), user1.getPassword())).thenReturn(false);

        assertThrows(UserPassCombinationException.class, () ->userService.login(credentials));
    }

}