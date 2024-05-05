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
public class TrainerDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

}
