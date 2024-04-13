package com.ashish.authorityservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String gender;
    private Integer age;
    private String city;
    private String dailyActivity;
    private String medicalConditions;
    private String emotionalHealth;
    private Double height;
    private Double weight;
    private Double targetWeight;
    private String imageName;
    private String imagePublicId;
    private boolean isPremium;
    private LocalDateTime trialValid;
    private LocalDateTime trainerValid;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;



}
