package com.matrimony.matrimony_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimony.matrimony_backend.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_professional_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfessionalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    private String highestEducation;

    private String educationDetails;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private String occupation;

    private String annualIncome;

    private String workLocation;

    private String companyName;
}
