package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.auth.LoginRequestDTO;
import com.exist.helpdesk.dto.auth.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}
