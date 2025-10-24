package com.exist.helpdesk.service.impl;

import com.exist.helpdesk.dto.auth.LoginRequestDTO;
import com.exist.helpdesk.dto.auth.LoginResponseDTO;
import com.exist.helpdesk.service.AuthService;
import com.exist.helpdesk.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

@Service
public class  AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = jwtUtils.generateToken(userDetails.getUsername(), authorities);
        String role = authorities.get(0);
        return new LoginResponseDTO(token, userDetails.getUsername(), role);
    }
}
