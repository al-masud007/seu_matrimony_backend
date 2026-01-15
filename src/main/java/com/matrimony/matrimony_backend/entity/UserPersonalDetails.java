package com.matrimony.matrimony_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimony.matrimony_backend.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_personal_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private Boolean hasChildren;

    private Integer noOfChildren;

    private Boolean childrenLivingWith;

    private Double height; // in cm

    private Double weight; // in kg

    private String religion;

    private String caste;

    private String motherTongue;
}
