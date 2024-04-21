package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.*;
import com.ashish.authorityservice.model.Trainer;
import com.ashish.authorityservice.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    void registerUser(UserDto userDto);

    UserAddResponse addUser(UserAddRequest req, MultipartFile image);

    User getUserDetails(UUID userId);

    UserAddResponse updateUser(UserAddRequest userAddRequest, MultipartFile file);

    void updatePayment(PaymentData paymentData);

    void updateTrainerPayment(TrainerPaymentData paymentData);

    UserAddResponse editUser(UserAddRequest editReq);

    Trainer getUserTrainer(UUID userId);

    CommonResponseDTO saveBMI(UUID userId);

    TargetWeightResponse findTargetWeightRange(UUID userId);
}
