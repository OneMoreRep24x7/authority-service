package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.CertificateDto;
import com.ashish.authorityservice.dto.TrainerDto;
import com.ashish.authorityservice.dto.TrainerProfileRequest;
import com.ashish.authorityservice.dto.TrainerProfileResponse;
import com.ashish.authorityservice.model.Certificates;
import com.ashish.authorityservice.model.Trainer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TrainerService {
    void registerTrainer(TrainerDto trainerDto);
    TrainerProfileResponse addTrainer(TrainerProfileRequest trainerReq, MultipartFile file);

    CertificateDto addCertificate(MultipartFile[] files, UUID trainerId);

    List<Certificates> getAllCertificatesById(UUID trainerId);

    TrainerProfileResponse getTrainerById(UUID trainerId);
}
