package com.ashish.authorityservice.service;

import com.ashish.authorityservice.configuartion.feignClient.AuthProxy;
import com.ashish.authorityservice.dto.*;
import com.ashish.authorityservice.model.Trainer;
import com.ashish.authorityservice.model.User;
import com.ashish.authorityservice.repository.TrainerRepository;
import com.ashish.authorityservice.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private CloudinaryImgService cloudinaryImgService;
    @Autowired
    private AuthProxy authProxy;

    @Override
    public void registerUser(UserDto userDto) {
        User user = User
                .builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .isPremium(userDto.isPremium())
                .trialValid(userDto.getTrialValid())
                .build();
        userRepository.save(user);
    }

    @Override
    public UserAddResponse addUser(UserAddRequest req, MultipartFile img) {
        UUID userId = req.getId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            String folder = "profile_pictures";
            Map data = cloudinaryImgService.upload(img, folder);
            String imageUrl = (String) data.get("secure_url");
            String publicId = (String) data.get("public_id");
            User user = optionalUser.get();
            user.setGender(req.getGender());
            user.setAge(req.getAge());
            user.setDailyActivity(req.getDailyActivity());
            user.setEmotionalHealth(req.getEmotionalHealth());
            user.setPhone(req.getPhone());
            user.setCity(req.getCity());
            user.setMedicalConditions(req.getMedicalConditions());
            user.setHeight(req.getHeight());
            user.setWeight(req.getWeight());
            user.setImageName(imageUrl);
            user.setImagePublicId(publicId);

            User savedUser = userRepository.save(user);
            return UserAddResponse.builder()
                    .user(savedUser)
                    .message("User added successfully...")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
        return UserAddResponse.builder()
                .message("User not found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @Override
    public User getUserDetails(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }
        return null;
    }

    @Override
    public UserAddResponse updateUser(UserAddRequest req, MultipartFile img) {
        UUID userId = req.getId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            String folder = "profile_pictures";
            Map data = cloudinaryImgService.upload(img, folder);
            String imageUrl = (String) data.get("secure_url");
            String publicId = (String) data.get("public_id");
            User user = optionalUser.get();
            user.setGender(req.getGender());
            user.setAge(req.getAge());
            user.setDailyActivity(req.getDailyActivity());
            user.setEmotionalHealth(req.getEmotionalHealth());
            user.setPhone(req.getPhone());
            user.setCity(req.getCity());
            user.setMedicalConditions(req.getMedicalConditions());
            user.setHeight(req.getHeight());
            user.setWeight(req.getWeight());
            user.setImageName(imageUrl);
            user.setImagePublicId(publicId);
            User savedUser = userRepository.save(user);
            return UserAddResponse.builder()
                    .user(savedUser)
                    .message("User updated successfully...")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
        return UserAddResponse.builder()
                .message("User not found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }
    @Override
    public UserAddResponse editUser(UserAddRequest req) {
        UUID userId = req.getId();
        System.out.println(userId+">>>>>userId");
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setGender(req.getGender());
            user.setAge(req.getAge());
            user.setDailyActivity(req.getDailyActivity());
            user.setEmotionalHealth(req.getEmotionalHealth());
            user.setPhone(req.getPhone());
            user.setCity(req.getCity());
            user.setMedicalConditions(req.getMedicalConditions());
            user.setHeight(req.getHeight());
            user.setWeight(req.getWeight());
            user.setImageName(user.getImageName());
            user.setImagePublicId(user.getImagePublicId());
            user.setPremium(user.isPremium());
            user.setTrialValid(user.getTrialValid());
            user.setTrainerValid(user.getTrainerValid());
            user.setTrainer(user.getTrainer());
            User savedUser = userRepository.save(user);
            return UserAddResponse.builder()
                    .user(savedUser)
                    .message("User updated successfully...")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }

        return UserAddResponse.builder()
                .message("User not found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @Override
    public Trainer getUserTrainer(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            Trainer trainer = user.getTrainer();
            return trainer;
        }
        return null;
    }

    @Override
    public CommonResponseDTO saveBMI(UUID userId) {
        User user = userRepository.findById(userId).get();
        double height = user.getHeight();
        double weight = user.getWeight();
        double heightInMeter = height/100;
        double BMI = weight/(heightInMeter*heightInMeter);
        BMI = Double.parseDouble(String.format("%.1f", BMI));
        user.setBMI(BMI);
        userRepository.save(user);
        return CommonResponseDTO.builder()
                .message("BMI updated successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public TargetWeightResponse findTargetWeightRange(UUID userId) {
        User user = userRepository.findById(userId).get();

        double height = user.getHeight()/100;
        double startRange = 18.5*(height*height);
        double endRange = 24.9*(height*height);
        startRange = Double.parseDouble(String.format("%.1f", startRange));
        endRange = Double.parseDouble(String.format("%.1f", endRange));
        return TargetWeightResponse.builder()
                .startRange(startRange)
                .endRange(endRange)
                .build();
    }

    @Override
    public TrainerProfileResponse findTrainerByUserId(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Trainer trainer = user.getTrainer();
            if(trainer!=null && user.getTrainerValid().isAfter(LocalDateTime.now())){
                return TrainerProfileResponse.builder()
                        .trainer(trainer)
                        .message("Trainer fetched successfully...")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }
            return TrainerProfileResponse.builder()
                    .message("Trainer not found or Expired..")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();

        }
        return TrainerProfileResponse.builder()
                .message("No user found!")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    @Override
    public CommonResponseDTO blockUser(UUID userId) {
        User user = userRepository.findById(userId).get();
        user.setActive(false);
        userRepository.save(user);
        return CommonResponseDTO.builder()
                .message("User blocked successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CommonResponseDTO UnBlockUser(UUID userId) {
        User user = userRepository.findById(userId).get();
        user.setActive(true);
        userRepository.save(user);
        return CommonResponseDTO.builder()
                .message("User unblocked successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public void updatePayment(PaymentData paymentData) {
        UUID userId = paymentData.getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LocalDateTime now = LocalDateTime.now();
            if (paymentData.getAmount() == 800) {
                // this user is valid for 6 months
                user.setTrialValid(now.plusMonths(6));
                user.setPremium(true);
            } else {
                // this user is valid for 1 month
                user.setTrialValid(now.plusMonths(1));
                user.setPremium(true);
            }
            userRepository.save(user);
            authProxy.updatePayment(paymentData);
        } else {
            throw new RuntimeException("User with id " + userId + " not found");
        }
    }

    @Override
    public void updateTrainerPayment(TrainerPaymentData paymentData) {
        UUID userId = paymentData.getUserId();
        UUID trainerId = paymentData.getTrainerId();
        User user = userRepository.findById(userId).get();
        Trainer trainer = trainerRepository.findById(trainerId).get();
        LocalDateTime now = LocalDateTime.now();
        if (paymentData.getAmount() == 1000) {
            user.setTrainerValid(now.plusMonths(1));
        } else if (paymentData.getAmount() == 8000) {
            user.setTrainerValid(now.plusMonths(6));
        } else {
            user.setTrainerValid(now.plusMonths(12));
        }
        user.setTrainer(trainer);
        trainer.setSlots(trainer.getSlots()-1);
        trainer.setClients(trainer.getClients()+1);
        trainer.getUsers().add(user);
        userRepository.save(user);
        trainerRepository.save(trainer);
    }


}

