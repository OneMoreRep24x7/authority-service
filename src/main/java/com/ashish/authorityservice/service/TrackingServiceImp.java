package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.*;
import com.ashish.authorityservice.model.Tracking;
import com.ashish.authorityservice.model.User;
import com.ashish.authorityservice.repository.TrackingRepository;
import com.ashish.authorityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrackingServiceImp implements TrackingService{
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Override
    public CommonResponseDTO setPrimaryGoal(PrimaryGoalParams primaryGoalParams) {

        UUID userId = primaryGoalParams.getUserId();
        Optional<Tracking> optionalTracking = trackingRepository.findByUserId(userId);
        if(optionalTracking.isPresent()){
            return CommonResponseDTO.builder()
                    .message("Tracking already created")
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        }else {
            User user = userRepository.findById(userId).orElse(null);

            double currentWeight = user.getWeight();
            double height = user.getHeight();
            String gender = user.getGender();
            String dailyActivity = user.getDailyActivity();
            double targetWeight = primaryGoalParams.getTargetWeight();
            String primaryGoal = primaryGoalParams.getPrimaryGoal();

            double BMR;
            double TDEE;
            double caloriesNeededPerDay;
            double caloriesBurnedPerDay = 0; // Initialize to 0

            // Calculate BMR based on gender
            if (gender.equalsIgnoreCase("male")) {
                BMR = 10 * currentWeight + 6.25 * height - 5 * user.getAge() + 5;
            } else {
                BMR = 10 * currentWeight + 6.25 * height - 5 * user.getAge() - 161;
            }

            // Adjust BMR based on activity level
            switch (dailyActivity) {
                case "Little or no activity":
                    TDEE = BMR * 1.2;
                    break;
                case "Lightly Active":
                    TDEE = BMR * 1.375;
                    break;
                case "Moderately Active":
                    TDEE = BMR * 1.55;
                    break;
                case "Very Active":
                    TDEE = BMR * 1.725;
                    break;
                default:
                    TDEE = BMR;
                    break;
            }

            // Calculate calories needed to achieve target weight
            if (targetWeight > currentWeight) {
                caloriesNeededPerDay = TDEE + 500; // Add 500 calories for weight gain
            } else {
                caloriesNeededPerDay = TDEE - 500; // Subtract 500 calories for weight loss
            }

            // Adjust calories for workout calories burned
            double workoutCaloriesBurned = 250; // Calories burned per workout
            if (primaryGoal.equalsIgnoreCase("gain")) {
                caloriesBurnedPerDay = workoutCaloriesBurned;
            } else if (primaryGoal.equalsIgnoreCase("lose")) {
                caloriesBurnedPerDay = 1.5 * workoutCaloriesBurned; // Burn extra calories for weight loss
            }


            // Calculate water intake
            double litersNeedPerKg = 1.0 / 20; // 1 liter per 20 kg
            double litersOfWaterNeeded = litersNeedPerKg * currentWeight;
            double noOfGlass = Math.ceil(litersOfWaterNeeded / 0.25);// 1 glass is equal to 0.25 liters

            Tracking tracking = Tracking.builder()
                    .userId(userId)
                    .primaryGoal(primaryGoalParams.getPrimaryGoal())
                    .targetWeight(primaryGoalParams.getTargetWeight())
                    .currentWeight(user.getWeight())
                    .caloriesNeeded((int) Math.round(caloriesNeededPerDay))
                    .workoutBurn(caloriesBurnedPerDay)
                    .waterIntake(noOfGlass)
                    .trackingDate(LocalDate.now())
                    .build();
            trackingRepository.save(tracking);

            return CommonResponseDTO.builder()
                    .message("Tracking details created")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
    }

    @Override
    public TrackingDetailsDTO getTrackingDetails(UUID userId) {
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){
            Tracking trackingDetails = optionalTracking.get();
            return TrackingDetailsDTO.builder()
                    .details(trackingDetails)
                    .message("Details fetched successfully...")
                    .statusCode(HttpStatus.OK.value())
                    .build();

        }
        return TrackingDetailsDTO.builder()
                .message("Tracking is not yet started!")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @Override
    public void updateCaloriesEaten(CaloriesEatenReqDTO caloriesEatenReqDTO) {
        UUID userId = caloriesEatenReqDTO.getUserId();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){

            LocalDate currentDate = LocalDate.now();
            double caloriesEaten = caloriesEatenReqDTO.getCaloriesEaten();
            Tracking tracking = optionalTracking.get();
            if(currentDate.equals(tracking.getTrackingDate())) {
                if (Double.compare(caloriesEaten, 0.0) < 0){
                    tracking.setCaloriesEaten(tracking.getCaloriesEaten()+caloriesEaten);
                    tracking.setTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);

                }else {
                    tracking.setCaloriesEaten(tracking.getCaloriesEaten()+caloriesEaten);
                    tracking.setTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);

                }


            }else{
                Tracking newTracking = Tracking.builder()
                        .userId(userId)
                        .primaryGoal(tracking.getPrimaryGoal())
                        .targetWeight(tracking.getTargetWeight())
                        .currentWeight(tracking.getCurrentWeight())
                        .caloriesNeeded(tracking.getCaloriesNeeded())
                        .workoutBurn(tracking.getWorkoutBurn())
                        .waterIntake(tracking.getWaterIntake())
                        .trackingDate(LocalDate.now())
                        .weightTrackingDate(LocalDate.now())
                        .caloriesEaten(caloriesEaten)
                        .caloriesBurned(0)
                        .waterConsumed(0)
                        .build();
                Tracking savedTracking = trackingRepository.save(newTracking);

            }


        }

    }

    @Override
    public void updateCaloriesBurned(CaloriesBurnReqDTO caloriesBurnReqDTO) {
        UUID userId = caloriesBurnReqDTO.getUserId();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){

            LocalDate currentDate = LocalDate.now();
            double caloriesBurned = caloriesBurnReqDTO.getCaloriesBurned();
            Tracking tracking = optionalTracking.get();
            if(currentDate.equals(tracking.getTrackingDate())) {
                if (Double.compare(caloriesBurned, 0.0) < 0){
                    tracking.setCaloriesBurned(tracking.getCaloriesBurned() + caloriesBurned);
                    tracking.setTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);

                }else {
                    tracking.setCaloriesBurned(tracking.getCaloriesBurned()+caloriesBurned);
                    tracking.setTrackingDate(currentDate);
                    Tracking savedTracking = trackingRepository.save(tracking);

                }
            }else{
                Tracking newTracking = Tracking.builder()
                        .userId(userId)
                        .primaryGoal(tracking.getPrimaryGoal())
                        .targetWeight(tracking.getTargetWeight())
                        .currentWeight(tracking.getCurrentWeight())
                        .caloriesNeeded(tracking.getCaloriesNeeded())
                        .workoutBurn(tracking.getWorkoutBurn())
                        .waterIntake(tracking.getWaterIntake())
                        .trackingDate(LocalDate.now())
                        .caloriesEaten(0)
                        .caloriesBurned(caloriesBurned)
                        .waterConsumed(0)
                        .build();
                Tracking savedTracking = trackingRepository.save(newTracking);

            }


        }

    }

    @Override
    public TrackingDetailsDTO updateWaterConsumed(WaterIntakeReqDTO waterIntakeReqDTO) {
        UUID userId = waterIntakeReqDTO.getUserId();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){

            LocalDate currentDate = LocalDate.now();
            double waterConsumed = waterIntakeReqDTO.getWaterConsumed();
            Tracking tracking = optionalTracking.get();
            if(currentDate.equals(tracking.getTrackingDate())) {
                if (Double.compare(waterConsumed, 0.0) < 0){
                    tracking.setWaterConsumed(tracking.getWaterConsumed()+waterConsumed);
                    tracking.setTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);
                    return TrackingDetailsDTO.builder()
                            .details(savedTracking)
                            .message("water removed successfully")
                            .statusCode(HttpStatus.OK.value())
                            .build();
                }else{
                    tracking.setWaterConsumed(tracking.getWaterConsumed()+waterConsumed);
                    tracking.setTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);
                    return TrackingDetailsDTO.builder()
                            .details(savedTracking)
                            .message("water added successfully")
                            .statusCode(HttpStatus.OK.value())
                            .build();
                }

            }else{
                Tracking newTracking = Tracking.builder()
                        .userId(userId)
                        .primaryGoal(tracking.getPrimaryGoal())
                        .targetWeight(tracking.getTargetWeight())
                        .currentWeight(tracking.getCurrentWeight())
                        .caloriesNeeded(tracking.getCaloriesNeeded())
                        .workoutBurn(tracking.getWorkoutBurn())
                        .waterIntake(tracking.getWaterIntake())
                        .trackingDate(LocalDate.now())
                        .caloriesEaten(0)
                        .caloriesBurned(0)
                        .waterConsumed(waterConsumed)
                        .build();
                Tracking savedTracking = trackingRepository.save(newTracking);
                return  TrackingDetailsDTO.builder()
                        .details(savedTracking)
                        .message("water added successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }


        }
        return TrackingDetailsDTO.builder()
                .message("Tracking is not yet started!")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @Override
    public TrackingDetailsDTO updateWeight(WeightTrackReqDTO weightTrackReqDTO) {
        UUID userId = weightTrackReqDTO.getUserId();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){
            LocalDate currentDate = LocalDate.now();
            double updatedWeight = weightTrackReqDTO.getUpdatedWeight();;
            Tracking tracking = optionalTracking.get();
                if("gain".equals(tracking.getPrimaryGoal()) && updatedWeight >= tracking.getTargetWeight()){
                    TargetWeightResponse targetRange =userService.findTargetWeightRange(userId);
                    if(updatedWeight>targetRange.getEndRange()){
                        return TrackingDetailsDTO.builder()
                                .message("Can't update the weight since the selected weight is greater than your safe range ")
                                .statusCode(HttpStatus.CONFLICT.value()) //409
                                .build();
                    }
                    tracking.setCurrentWeight(updatedWeight);
                    tracking.setWeightTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);
                    return  TrackingDetailsDTO.builder()
                            .message("Congratulations you achieved your goal")
                            .statusCode(HttpStatus.CONTINUE.value()) //100
                            .build();
                } else if ("lose".equals(tracking.getPrimaryGoal()) && updatedWeight <= tracking.getCurrentWeight()) {
                    TargetWeightResponse targetRange =userService.findTargetWeightRange(userId);
                    if(updatedWeight<targetRange.getStartRange()){
                        return TrackingDetailsDTO.builder()
                                .message("Can't update the weight since the selected weight is greater than your safe range ")
                                .statusCode(HttpStatus.CONFLICT.value()) //409
                                .build();
                    }
                    tracking.setCurrentWeight(updatedWeight);
                    tracking.setWeightTrackingDate(currentDate);
                    Tracking savedTracking =  trackingRepository.save(tracking);
                    return  TrackingDetailsDTO.builder()
                            .message("Congratulations you achieved your goal")
                            .statusCode(HttpStatus.CONTINUE.value()) //100
                            .build();

                }else {
                    tracking.setCurrentWeight(updatedWeight);
                    tracking.setWeightTrackingDate(currentDate);
                    Tracking savedTracking = trackingRepository.save(tracking);

                    return TrackingDetailsDTO.builder()
                            .details(savedTracking)
                            .message("weight updated successfully")
                            .statusCode(HttpStatus.OK.value())
                            .build();
                }




        }
        return TrackingDetailsDTO.builder()
                .message("Tracking is not yet started!")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @Override
    public CaloriesTrackDTO getCalories(UUID userId) {
        LocalDate startDate = getStartOfLast10Days();
        LocalDate endDate = getYesterday();

        // Fetch tracking data for the specified date range
        List<Tracking> trackingData = trackingRepository.findByUserIdAndDateRange(
                userId, startDate, endDate);

        HashMap<String, Double> caloriesEaten = new HashMap<>();
        HashMap<String, Double> caloriesBurned = new HashMap<>();

        // Initialize with zeros for each of the last 10 days
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            caloriesEaten.put(date.toString(), 0.0);
            caloriesBurned.put(date.toString(), 0.0);
        }

        // Aggregate data for each of the last 10 days
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;
            List<Tracking> dailyTracking = trackingData.stream()
                    .filter(t -> t.getTrackingDate().isEqual(finalDate))
                    .collect(Collectors.toList());

            // Aggregate daily calories
            double dailyCaloriesEaten = dailyTracking.stream()
                    .mapToDouble(Tracking::getCaloriesEaten)
                    .sum();

            double dailyCaloriesBurned = dailyTracking.stream()
                    .mapToDouble(Tracking::getCaloriesBurned)
                    .sum();

            // Store data in the maps
            caloriesEaten.put(date.toString(), dailyCaloriesEaten);
            caloriesBurned.put(date.toString(), dailyCaloriesBurned);
        }

        // Return the DTO with the collected data
        return CaloriesTrackDTO.builder()
                .caloriesEaten(caloriesEaten)
                .caloriesBurned(caloriesBurned)
                .build();
    }


    public static LocalDate getStartOfLast10Days() {
        return LocalDate.now().minusDays(10);
    }

    public static LocalDate getYesterday() {
        return LocalDate.now().minusDays(1);
    }


}
