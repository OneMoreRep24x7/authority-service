package com.ashish.authorityservice.controller;

import com.ashish.authorityservice.dto.UpdateResponse;
import com.ashish.authorityservice.dto.UpdateRequest;
import com.ashish.authorityservice.dto.UserDto;
import com.ashish.authorityservice.model.User;
import com.ashish.authorityservice.service.CloudinaryImgService;
import com.ashish.authorityservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/register")
    public void userRegistration(@RequestBody UserDto userDto){
        userService.registerUser(userDto);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateResponse> updateUser(
            @RequestPart("file") MultipartFile file,
            @RequestParam("profileRequest") String profileRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        UpdateRequest updateRequest = objectMapper.readValue( profileRequest,UpdateRequest.class);
        return ResponseEntity.ok(userService.updateUser(updateRequest, file));
    }



}
