package com.vtb.task.service;


import com.vtb.task.dto.request.AuthenticationRequest;
import com.vtb.task.dto.request.RegisterRequest;
import com.vtb.task.dto.response.AuthenticationResponse;
import com.vtb.task.entity.Role;
import com.vtb.task.entity.Status;
import com.vtb.task.entity.User;
import com.vtb.task.repository.RoleRepository;
import com.vtb.task.repository.UserRepository;
import com.vtb.task.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Метод для регистрауии
     * @param request запрос на регистрауию {@link RegisterRequest}
     * @return возвращает {@link AuthenticationResponse} с jwt
     */
    public AuthenticationResponse register(RegisterRequest request) {
        List<Role> defaultRoles = new ArrayList<>();
        defaultRoles.add(roleRepository.findByName("ROLE_USER"));
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(defaultRoles)
                .build();
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date(System.currentTimeMillis()));
        user.setUpdated(new Date(System.currentTimeMillis()));
        user.setLastLogin(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Метод для аутентификации
     * @param request запрос на аутентификацию {@link AuthenticationResponse}
     * @return возвращает {@link AuthenticationResponse} с jwt
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        user.setLastLogin(new Date(System.currentTimeMillis()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
