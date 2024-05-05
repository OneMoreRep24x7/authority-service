package com.ashish.authorityservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;
    private String primaryGoal;
    private double targetWeight;
    private double currentWeight;
    private double caloriesNeeded;
    private double workoutBurn;
    private double caloriesBurned;
    private double  waterIntake;
    private double  waterConsumed;
    private double caloriesEaten;
    private LocalDate trackingDate;
    private LocalDate weightTrackingDate;
}
