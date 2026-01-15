package com.matrimony.matrimony_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    private Integer ageMin;
    private Integer ageMax;

    private Double heightMin;
    private Double heightMax;

    @ElementCollection
    @CollectionTable(name = "user_marital_status_prefs", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "marital_status")
    private List<String> maritalStatusPref;

    @ElementCollection
    @CollectionTable(name = "user_education_prefs", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "education")
    private List<String> educationPref;

    @ElementCollection
    @CollectionTable(name = "user_occupation_prefs", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "occupation")
    private List<String> occupationPref;

    @ElementCollection
    @CollectionTable(name = "user_location_prefs", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "location")
    private List<String> locationPref;
}
