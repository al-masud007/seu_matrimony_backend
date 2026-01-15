package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.dto.response.UserResponse;
import com.matrimony.matrimony_backend.entity.*;
import com.matrimony.matrimony_backend.repository.*;
import com.matrimony.matrimony_backend.service.ImageService;
import com.matrimony.matrimony_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPersonalDetailsRepository personalDetailsRepository;
    private final UserProfessionalDetailsRepository professionalDetailsRepository;
    private final UserFamilyDetailsRepository familyDetailsRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final ImageService imageService;

    @Override
    public UserResponse getCurrentUserProfile() {
        User user = getCurrentUser();
        return mapToUserResponse(user);
    }

    @Override
    @Transactional
    public UserPreferences updateUserPreferences(UserPreferences preferences) {
        User user = getCurrentUser();
        UserPreferences existing = user.getPreferences();

        if (existing == null) {
            preferences.setUser(user);
            return userPreferencesRepository.save(preferences);
        }

        preferences.setId(existing.getId());
        preferences.setUser(user);
        return userPreferencesRepository.save(preferences);
    }

    @Override
    @Transactional
    public String uploadProfilePhoto(String base64Data) {
        User user = getCurrentUser();
        
        // Upload to Firestore (Mock for now)
        String firestoreDocId = imageService.uploadImage(user.getId(), base64Data, "profile_photo");
        
        // Update User profilePhotoUrl with document ID
        user.setProfilePhotoUrl(firestoreDocId);
        userRepository.save(user);
        
        return firestoreDocId;
    }

    @Override
    @Transactional
    public UserPersonalDetails updatePersonalDetails(UserPersonalDetails details) {
        User user = getCurrentUser();
        UserPersonalDetails existing = user.getPersonalDetails();
        
        if (existing == null) {
            details.setUser(user);
            return personalDetailsRepository.save(details);
        }
        
        details.setId(existing.getId());
        details.setUser(user);
        return personalDetailsRepository.save(details);
    }

    @Override
    @Transactional
    public UserProfessionalDetails updateProfessionalDetails(UserProfessionalDetails details) {
        User user = getCurrentUser();
        UserProfessionalDetails existing = user.getProfessionalDetails();
        
        if (existing == null) {
            details.setUser(user);
            return professionalDetailsRepository.save(details);
        }
        
        details.setId(existing.getId());
        details.setUser(user);
        return professionalDetailsRepository.save(details);
    }

    @Override
    @Transactional
    public UserFamilyDetails updateFamilyDetails(UserFamilyDetails details) {
        User user = getCurrentUser();
        UserFamilyDetails existing = user.getFamilyDetails();
        
        if (existing == null) {
            details.setUser(user);
            return familyDetailsRepository.save(details);
        }
        
        details.setId(existing.getId());
        details.setUser(user);
        return familyDetailsRepository.save(details);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
                .build();
    }
}
