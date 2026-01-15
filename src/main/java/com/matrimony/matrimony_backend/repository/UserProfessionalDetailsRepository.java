package com.matrimony.matrimony_backend.repository;

import com.matrimony.matrimony_backend.entity.UserProfessionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfessionalDetailsRepository extends JpaRepository<UserProfessionalDetails, UUID> {
}
