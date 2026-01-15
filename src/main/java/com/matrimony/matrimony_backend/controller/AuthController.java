package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.request.LoginRequest;
import com.matrimony.matrimony_backend.dto.request.RegisterRequest;
import com.matrimony.matrimony_backend.dto.response.ApiResponse;
import com.matrimony.matrimony_backend.dto.response.AuthResponse;
import com.matrimony.matrimony_backend.dto.response.UserResponse;
import com.matrimony.matrimony_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUser(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.loginUser(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }
}
