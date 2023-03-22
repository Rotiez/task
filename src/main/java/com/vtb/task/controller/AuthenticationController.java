package com.vtb.task.controller;

import com.vtb.task.aspect.Audit;
import com.vtb.task.dto.request.AuthenticationRequest;
import com.vtb.task.dto.request.RegisterRequest;
import com.vtb.task.dto.response.AuthenticationResponse;
import com.vtb.task.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Контроллер, для запросов на регитсрацию и аутентификацию
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    /**
     * @param request объект класса {@link RegisterRequest}
     * @return {@link ResponseEntity<AuthenticationResponse>}
     */
    @Audit
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * @param request объект класса {@link AuthenticationRequest}
     * @return {@link ResponseEntity<AuthenticationResponse>}
     */
    @Audit
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
