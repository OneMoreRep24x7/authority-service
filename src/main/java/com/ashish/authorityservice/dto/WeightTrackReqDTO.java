package com.ashish.authorityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WeightTrackReqDTO {
    private UUID userId;
    private double updatedWeight;
}
