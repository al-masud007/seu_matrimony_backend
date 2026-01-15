package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.request.LoginRequest;
import com.matrimony.matrimony_backend.dto.request.RegisterRequest;
import com.matrimony.matrimony_backend.dto.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse userResponse = authService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        UserResponse userResponse = authService.loginUser(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", userResponse));
    }
}
