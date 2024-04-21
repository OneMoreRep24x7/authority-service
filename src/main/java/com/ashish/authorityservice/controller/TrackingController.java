package com.ashish.authorityservice.controller;



import com.ashish.authorityservice.dto.*;
import com.ashish.authorityservice.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;
    @PostMapping("/setPrimaryGoal")
    public ResponseEntity<CommonResponseDTO> setPrimaryGoal(
            @RequestBody PrimaryGoalParams primaryGoalParams
    ){
        return ResponseEntity.ok(trackingService.setPrimaryGoal(primaryGoalParams));
    }

    @GetMapping("/getTrackingDetails")
    public ResponseEntity<TrackingDetailsDTO> getTrackingDetails(
            @RequestParam UUID userId
            ){
        return ResponseEntity.ok(trackingService.getTrackingDetails(userId));
    }

    //for updating calories eaten
    @PostMapping("/updateCaloriesEaten")
    public ResponseEntity<TrackingDetailsDTO> updateCaloriesEaten(
            @RequestBody CaloriesEatenReqDTO caloriesEatenReqDTO
            ){
        return ResponseEntity.ok(trackingService.updateCaloriesEaten(caloriesEatenReqDTO));
    }
    //for updating calories burned
    @PostMapping("/updateCaloriesBurned")
    public ResponseEntity<TrackingDetailsDTO> updateCaloriesBurned(
            @RequestBody CaloriesBurnReqDTO caloriesBurnReqDTO
            ){
        return ResponseEntity.ok(trackingService.updateCaloriesBurned(caloriesBurnReqDTO));
    }

    //for updating water intake
    @PostMapping("/updateWaterConsumed")
    public ResponseEntity<TrackingDetailsDTO> updateWaterConsumed(
            @RequestBody WaterIntakeReqDTO waterIntakeReqDTO
    ){
        System.out.println(waterIntakeReqDTO+">>>>>>>");
        return ResponseEntity.ok(trackingService.updateWaterConsumed(waterIntakeReqDTO));
    }

    //for updating weight
    @PostMapping("/updateWeight")
    public ResponseEntity<TrackingDetailsDTO>updateWeight(
            @RequestBody WeightTrackReqDTO weightTrackReqDTO
    ){
        return ResponseEntity.ok(trackingService.updateWeight(weightTrackReqDTO));
    }
}
