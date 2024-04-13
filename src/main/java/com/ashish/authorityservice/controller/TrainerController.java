package com.ashish.authorityservice.controller;

import com.ashish.authorityservice.dto.*;
import com.ashish.authorityservice.model.Certificates;
import com.ashish.authorityservice.model.Trainer;
import com.ashish.authorityservice.service.TrainerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trainer")
public class TrainerController {
    @Autowired
    TrainerService trainerService;

    @PostMapping("/register")
    public void registerTrainer(@RequestBody TrainerDto trainerDto){
        trainerService.registerTrainer(trainerDto);
    }

    @PostMapping("/addProfile")
    public ResponseEntity<TrainerProfileResponse> addTrainer(
            @RequestPart("file") MultipartFile file,
            @RequestParam("profileRequest") String profileRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        TrainerProfileRequest trainerAddRequest = objectMapper.readValue(profileRequest, TrainerProfileRequest.class);
        return ResponseEntity.ok(trainerService.addTrainer(trainerAddRequest,file));
    }

    @PostMapping("/editTrainerProfile")
    public ResponseEntity<TrainerProfileResponse> editTrainer(
            @RequestBody TrainerProfileRequest editRequest
    ){
        return ResponseEntity.ok(trainerService.editTrainer(editRequest));
    }

    @PostMapping("/addCertificates")
    public ResponseEntity<CertificateDto> addCertificate(
            @RequestParam("trainerId") UUID trainerId,
            @RequestParam("files") MultipartFile[] files
    ){
        return ResponseEntity.ok(trainerService.addCertificate(files,trainerId));
    }

    @GetMapping("/getTrainerCertificates")
    public ResponseEntity<List<Certificates>> getAllCertificate(
            @RequestParam("trainerId") UUID trainerId
    ){
        return ResponseEntity.ok(trainerService.getAllCertificatesById(trainerId));
    }

    @GetMapping("/getTrainerById")
    public ResponseEntity<TrainerProfileResponse> getTrainerById(
            @RequestParam("trainerId") UUID trainerId
    ){
        return ResponseEntity.ok(trainerService.getTrainerById(trainerId));
    }

    @GetMapping("/getAllTrainers")
    public ResponseEntity<List<Trainer>> getAllTrainers(){
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }


}
