package com.matrimony.matrimony_backend.service;

import com.matrimony.matrimony_backend.dto.request.LoginRequest;
import com.matrimony.matrimony_backend.dto.request.RegisterRequest;
import com.matrimony.matrimony_backend.dto.response.UserResponse;

public interface AuthService {
    UserResponse registerUser(RegisterRequest request);
    UserResponse loginUser(LoginRequest request);
}
