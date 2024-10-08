package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.dto.CredentialsDto;
import com.example.projecttrackingapi.dto.LoginResponseDto;
import com.example.projecttrackingapi.security.JwtUtil;
import com.example.projecttrackingapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody CredentialsDto credentials) {
        final var user = userService.login(credentials);
        final var jwtToken = jwtUtil.createToken(user);
        return ResponseEntity.ok()
                .body(new LoginResponseDto(jwtToken));
    }
}
