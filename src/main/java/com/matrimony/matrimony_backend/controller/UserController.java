package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.response.ApiResponse;
import com.matrimony.matrimony_backend.dto.response.UserResponse;
import com.matrimony.matrimony_backend.entity.UserFamilyDetails;
import com.matrimony.matrimony_backend.entity.UserPersonalDetails;
import com.matrimony.matrimony_backend.entity.UserProfessionalDetails;
import com.matrimony.matrimony_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", userService.getCurrentUserProfile()));
    }

    @PutMapping("/me/personal")
    public ResponseEntity<ApiResponse<UserPersonalDetails>> updatePersonalDetails(@RequestBody UserPersonalDetails details) {
        return ResponseEntity.ok(ApiResponse.success("Personal details updated successfully", userService.updatePersonalDetails(details)));
    }

    @PutMapping("/me/professional")
    public ResponseEntity<ApiResponse<UserProfessionalDetails>> updateProfessionalDetails(@RequestBody UserProfessionalDetails details) {
        return ResponseEntity.ok(ApiResponse.success("Professional details updated successfully", userService.updateProfessionalDetails(details)));
    }

    @PutMapping("/me/family")
    public ResponseEntity<ApiResponse<UserFamilyDetails>> updateFamilyDetails(@RequestBody UserFamilyDetails details) {
        return ResponseEntity.ok(ApiResponse.success("Family details updated successfully", userService.updateFamilyDetails(details)));
    }
}
