package com.ashish.authorityservice.dto;

import com.ashish.authorityservice.model.Tracking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TrackingDetailsDTO {
    private Tracking details;
    private String message;
    private int statusCode;
}
