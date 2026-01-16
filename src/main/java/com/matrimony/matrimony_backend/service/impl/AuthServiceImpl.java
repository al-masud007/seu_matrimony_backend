package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.dto.request.LoginRequest;
import com.matrimony.matrimony_backend.dto.request.RegisterRequest;
import com.matrimony.matrimony_backend.dto.response.AuthResponse;
import com.matrimony.matrimony_backend.dto.response.UserResponse;
import com.matrimony.matrimony_backend.entity.User;
import com.matrimony.matrimony_backend.enums.Role;
import com.matrimony.matrimony_backend.enums.VerificationStatus;
import com.matrimony.matrimony_backend.exception.BadRequestException;
import com.matrimony.matrimony_backend.repository.UserRepository;
import com.matrimony.matrimony_backend.security.JwtTokenProvider;
import com.matrimony.matrimony_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .profileFor(request.getProfileFor())
                .gender(request.getGender())
                .dob(request.getDob())
                .role(Role.USER)
                .verificationStatus(VerificationStatus.PENDING)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .user(mapToUserResponse(savedUser))
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .user(mapToUserResponse(user))
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .profileFor(user.getProfileFor())
                .gender(user.getGender())
                .dob(user.getDob())
                .role(user.getRole())
                .verificationStatus(user.getVerificationStatus())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .personalDetails(user.getPersonalDetails())
                .professionalDetails(user.getProfessionalDetails())
                .familyDetails(user.getFamilyDetails())
                .preferences(user.getPreferences())
                .build();
    }
}
