package com.exist.helpdesk.controller;

import com.exist.helpdesk.dto.auth.LoginRequestDTO;
import com.exist.helpdesk.dto.auth.LoginResponseDTO;
import com.exist.helpdesk.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }
}
