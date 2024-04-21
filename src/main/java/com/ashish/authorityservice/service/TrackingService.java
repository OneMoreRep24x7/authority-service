package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.*;

import java.util.UUID;

public interface TrackingService {
    CommonResponseDTO setPrimaryGoal(PrimaryGoalParams primaryGoalParams);

    TrackingDetailsDTO getTrackingDetails(UUID userId);

    TrackingDetailsDTO updateCaloriesEaten(CaloriesEatenReqDTO caloriesEatenReqDTO);

    TrackingDetailsDTO updateCaloriesBurned(CaloriesBurnReqDTO caloriesBurnReqDTO);

    TrackingDetailsDTO updateWaterConsumed(WaterIntakeReqDTO waterIntakeReqDTO);

    TrackingDetailsDTO updateWeight(WeightTrackReqDTO weightTrackReqDTO);
}
