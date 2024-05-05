package com.ashish.authorityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CaloriesTrackDTO {
    private HashMap<String,Double> caloriesEaten;
    private HashMap<String,Double>  caloriesBurned;
}
