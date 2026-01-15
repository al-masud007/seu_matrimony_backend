package com.matrimony.matrimony_backend.dto.response;

import com.matrimony.matrimony_backend.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponse {
    private UUID id;
    private String fullName;
    private Integer age;
    private Double height;
    private String religion;
    private String education;
    private String occupation;
    private String location;
    private String profilePhotoUrl;
    private Gender gender;
}
