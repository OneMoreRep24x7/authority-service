package com.ashish.authorityservice.dto;

import com.ashish.authorityservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResponse {

    private User user;
    private String message;
    private int statusCode;



}
