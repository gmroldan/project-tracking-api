package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.CredentialsDto;
import com.example.projecttrackingapi.dto.UserDto;

public interface AuthenticationService {

    UserDto login(CredentialsDto credentials);
}
