package com.ashish.authorityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PrimaryGoalParams {
    private UUID userId;
    private String primaryGoal;
    private double targetWeight;
}
