package com.ashish.authorityservice.controller;

import com.ashish.authorityservice.dto.UserAddResponse;
import com.ashish.authorityservice.dto.UserAddRequest;
import com.ashish.authorityservice.dto.UserDto;
import com.ashish.authorityservice.model.User;
import com.ashish.authorityservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/add")
    public ResponseEntity<UserAddResponse> addUser(
            @RequestPart("file") MultipartFile file,
            @RequestParam("profileRequest") String profileRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserAddRequest userAddRequest = objectMapper.readValue( profileRequest, UserAddRequest.class);
        return ResponseEntity.ok(userService.addUser(userAddRequest, file));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<User> getDetails(@RequestParam("userId") UUID userId){
        return ResponseEntity.ok(userService.getUserDetails(userId));
    }



}
