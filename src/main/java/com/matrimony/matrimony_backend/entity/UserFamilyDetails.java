package com.matrimony.matrimony_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimony.matrimony_backend.enums.FamilyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_family_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFamilyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    private String fatherOccupation;

    private String motherOccupation;

    private Integer noOfBrothers;

    private Integer noOfSisters;

    @Enumerated(EnumType.STRING)
    private FamilyType familyType;

    private String familyStatus;
}
