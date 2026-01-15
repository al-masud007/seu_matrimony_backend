package com.matrimony.matrimony_backend.dto.response;

import com.matrimony.matrimony_backend.enums.Gender;
import com.matrimony.matrimony_backend.enums.ProfileFor;
import com.matrimony.matrimony_backend.enums.Role;
import com.matrimony.matrimony_backend.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private ProfileFor profileFor;
    private Gender gender;
    private LocalDate dob;
    private Role role;
    private VerificationStatus verificationStatus;
    private String profilePhotoUrl;
}
