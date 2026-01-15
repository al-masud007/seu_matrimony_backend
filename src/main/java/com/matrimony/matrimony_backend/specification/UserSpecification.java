package com.matrimony.matrimony_backend.specification;

import com.matrimony.matrimony_backend.entity.*;
import com.matrimony.matrimony_backend.enums.Gender;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class UserSpecification {

    public static Specification<User> filterMatches(User currentUser, UserPreferences prefs) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new java.util.ArrayList<>();

            // 1. Exclude self
            predicates.add(cb.notEqual(root.get("id"), currentUser.getId()));

            // 2. Filter by opposite gender
            Gender oppositeGender = currentUser.getGender() == Gender.MALE ? Gender.FEMALE : Gender.MALE;
            predicates.add(cb.equal(root.get("gender"), oppositeGender));

            // 3. Age Range
            if (prefs.getAgeMin() != null) {
                LocalDate maxDob = LocalDate.now().minusYears(prefs.getAgeMin());
                predicates.add(cb.lessThanOrEqualTo(root.get("dob"), maxDob));
            }
            if (prefs.getAgeMax() != null) {
                LocalDate minDob = LocalDate.now().minusYears(prefs.getAgeMax() + 1);
                predicates.add(cb.greaterThan(root.get("dob"), minDob));
            }

            // 4. Height Range (from PersonalDetails)
            Join<User, UserPersonalDetails> personalJoin = root.join("personalDetails", JoinType.LEFT);
            if (prefs.getHeightMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(personalJoin.get("height"), prefs.getHeightMin()));
            }
            if (prefs.getHeightMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(personalJoin.get("height"), prefs.getHeightMax()));
            }

            // 5. Marital Status Pref
            if (prefs.getMaritalStatusPref() != null && !prefs.getMaritalStatusPref().isEmpty()) {
                predicates.add(personalJoin.get("maritalStatus").as(String.class).in(prefs.getMaritalStatusPref()));
            }

            // 6. Religion (usually a hard filter or pref, adding as Hard filter based on PersonalDetails if provided in search, but here we use prefs)
            // If we want to filter by same religion or specific religions in prefs
            
            // 7. Professional Prefs
            Join<User, UserProfessionalDetails> professionalJoin = root.join("professionalDetails", JoinType.LEFT);
            if (prefs.getEducationPref() != null && !prefs.getEducationPref().isEmpty()) {
                predicates.add(professionalJoin.get("highestEducation").in(prefs.getEducationPref()));
            }
            if (prefs.getOccupationPref() != null && !prefs.getOccupationPref().isEmpty()) {
                predicates.add(professionalJoin.get("occupation").in(prefs.getOccupationPref()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
