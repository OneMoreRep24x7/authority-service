package com.ashish.authorityservice.controller;

import com.ashish.authorityservice.dto.*;
import com.ashish.authorityservice.model.Trainer;
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

    @PostMapping("/updatePayment")
    public void updatePayment(
            @RequestBody PaymentData paymentData){
        userService.updatePayment(paymentData);
    }



    @PostMapping("/updateTrainerPayment")
    public void updateTrainerPayment(
            @RequestBody TrainerPaymentData paymentData
            ){
        userService.updateTrainerPayment(paymentData);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserAddResponse> updateUser(
            @RequestPart("file") MultipartFile file,
            @RequestParam("profileRequest") String profileRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserAddRequest userAddRequest = objectMapper.readValue(profileRequest, UserAddRequest.class);
        return ResponseEntity.ok(userService.updateUser(userAddRequest,file));
    }

    @PostMapping("/editUser")
    public ResponseEntity<UserAddResponse> EditUser(
            @RequestBody UserAddRequest editReq
    ){
        return ResponseEntity.ok(userService.editUser(editReq));
    }

    @GetMapping("/getUserTrainer")
    public ResponseEntity<Trainer> getUserTrainer(
            @RequestParam UUID userId
    ){
        return ResponseEntity.ok(userService.getUserTrainer(userId));
    }

}
