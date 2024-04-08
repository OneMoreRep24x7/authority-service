package com.ashish.authorityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRequest {
    private UUID id;
    private String gender;
    private Integer age;
    private String dailyActivity;
    private String emotionalHealth;
    private String phone;
    private String city;
    private String medicalConditions;
    private Double height;
    private Double weight;
    private Double targetWeight;

}
