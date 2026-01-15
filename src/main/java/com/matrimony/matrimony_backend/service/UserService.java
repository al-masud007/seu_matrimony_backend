package com.matrimony.matrimony_backend.service;

import com.matrimony.matrimony_backend.dto.response.UserResponse;
import com.matrimony.matrimony_backend.entity.UserFamilyDetails;
import com.matrimony.matrimony_backend.entity.UserPersonalDetails;
import com.matrimony.matrimony_backend.entity.UserProfessionalDetails;

public interface UserService {
    UserResponse getCurrentUserProfile();
    UserPersonalDetails updatePersonalDetails(UserPersonalDetails details);
    UserProfessionalDetails updateProfessionalDetails(UserProfessionalDetails details);
    UserFamilyDetails updateFamilyDetails(UserFamilyDetails details);
}
