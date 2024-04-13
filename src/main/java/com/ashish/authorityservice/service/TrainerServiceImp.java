package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.CertificateDto;
import com.ashish.authorityservice.dto.TrainerDto;
import com.ashish.authorityservice.dto.TrainerProfileRequest;
import com.ashish.authorityservice.dto.TrainerProfileResponse;
import com.ashish.authorityservice.model.Certificates;
import com.ashish.authorityservice.model.Trainer;
import com.ashish.authorityservice.model.User;
import com.ashish.authorityservice.repository.CertificateRepository;
import com.ashish.authorityservice.repository.TrainerRepository;
import com.ashish.authorityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerServiceImp implements TrainerService{
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private CloudinaryImgService cloudinaryImgService;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerTrainer(TrainerDto trainerDto) {
        Trainer trainer = Trainer.builder()
                .id(trainerDto.getId())
                .firstName(trainerDto.getFirstName())
                .lastName(trainerDto.getLastName())
                .email(trainerDto.getEmail())
                .role(trainerDto.getRole())
                .slots(10)
                .clients(0)
                .build();
        trainerRepository.save(trainer);
    }

    @Override
    public TrainerProfileResponse addTrainer(TrainerProfileRequest trainerReq, MultipartFile file) {
        UUID trainerId = trainerReq.getId();
        Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerId);
        if(optionalTrainer.isPresent()){
            String  folder = "trainer_profile";
            Map data =  cloudinaryImgService.upload(file,folder);
            String imageUrl = (String) data.get("secure_url");
            String publicId = (String) data.get("public_id");
            Trainer trainer = optionalTrainer.get();
            trainer.setQualifications(trainerReq.getQualification());
            trainer.setSlots(trainerReq.getSlots());
            trainer.setImageName(imageUrl);
            trainer.setImagePublicId(publicId);

            Trainer savedTrainer = trainerRepository.save(trainer);
            return TrainerProfileResponse.builder()
                    .trainer(savedTrainer)
                    .message("Trainer profile added successfully..")
                    .statusCode(HttpStatus.OK.value())
                    .build();


        }
        return TrainerProfileResponse.builder()
                .message("Trainer not exist")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }
    @Override
    public TrainerProfileResponse editTrainer(TrainerProfileRequest trainerReq) {
        UUID trainerId = trainerReq.getId();
        Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerId);
        if(optionalTrainer.isPresent()){
            Trainer trainer = optionalTrainer.get();
            trainer.setQualifications(trainerReq.getQualification());
            trainer.setSlots(trainerReq.getSlots());
            trainer.setImageName(trainer.getImageName());
            trainer.setImagePublicId(trainer.getImagePublicId());
            Trainer savedTrainer = trainerRepository.save(trainer);
            return TrainerProfileResponse.builder()
                    .trainer(savedTrainer)
                    .message("Trainer profile added successfully..")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
        return TrainerProfileResponse.builder()
                .message("Trainer not exist")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @Override
    public CertificateDto addCertificate(MultipartFile[] files,UUID trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).get();
        String folder = "Certificates";
        for(MultipartFile file : files){
            Map data =  cloudinaryImgService.upload(file,folder);
            String imageUrl = (String) data.get("secure_url");
            String publicId = (String) data.get("public_id");
            Certificates certificates = Certificates.builder()
                    .trainer(trainer)
                    .imageName(imageUrl)
                    .imagePublicId(publicId)
                    .build();
            certificateRepository.save(certificates);
        }
        return CertificateDto.builder()
                .message("Certificates added successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public List<Certificates> getAllCertificatesById(UUID trainerId) {
        return  trainerRepository.findCertificatesByTrainerId(trainerId);
    }

    @Override
    public TrainerProfileResponse getTrainerById(UUID trainerId) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerId);
        if(optionalTrainer.isPresent()){
            Trainer trainer = optionalTrainer.get();
            return TrainerProfileResponse.builder()
                    .trainer(trainer)
                    .message("Trainer fetched successfully")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
        return TrainerProfileResponse.builder()
                .message("Trainer not exist")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }




}
