package com.ashish.authorityservice.dto;

import com.ashish.authorityservice.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TrainerProfileResponse {

    private Trainer trainer;
    private String message;
    private int statusCode;
}
