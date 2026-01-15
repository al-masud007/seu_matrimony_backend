package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.dto.response.MatchResponse;
import com.matrimony.matrimony_backend.entity.User;
import com.matrimony.matrimony_backend.entity.UserPreferences;
import com.matrimony.matrimony_backend.repository.UserRepository;
import com.matrimony.matrimony_backend.service.MatchService;
import com.matrimony.matrimony_backend.specification.UserSpecification;
import com.matrimony.matrimony_backend.util.AgeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final UserRepository userRepository;

    @Override
    public Page<MatchResponse> getMatches(Pageable pageable) {
        User currentUser = getCurrentUser();
        UserPreferences prefs = currentUser.getPreferences();

        // If no preferences set, create a default filter (just opposite gender and exclude self)
        if (prefs == null) {
            prefs = new UserPreferences();
        }

        Specification<User> spec = UserSpecification.filterMatches(currentUser, prefs);
        Page<User> matchingUsers = userRepository.findAll(spec, pageable);

        return matchingUsers.map(this::mapToMatchResponse);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private MatchResponse mapToMatchResponse(User user) {
        return MatchResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .age(AgeCalculator.calculateAge(user.getDob()))
                .height(user.getPersonalDetails() != null ? user.getPersonalDetails().getHeight() : null)
                .religion(user.getPersonalDetails() != null ? user.getPersonalDetails().getReligion() : null)
                .education(user.getProfessionalDetails() != null ? user.getProfessionalDetails().getHighestEducation() : null)
                .occupation(user.getProfessionalDetails() != null ? user.getProfessionalDetails().getOccupation() : null)
                .location(user.getProfessionalDetails() != null ? user.getProfessionalDetails().getWorkLocation() : null)
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .gender(user.getGender())
                .build();
    }
}
