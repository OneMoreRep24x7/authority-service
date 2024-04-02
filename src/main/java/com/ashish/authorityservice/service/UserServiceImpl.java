package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.UpdateResponse;
import com.ashish.authorityservice.dto.UpdateRequest;
import com.ashish.authorityservice.dto.UserDto;
import com.ashish.authorityservice.model.User;
import com.ashish.authorityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    CloudinaryImgService cloudinaryImgService;

    @Override
    public void registerUser(UserDto userDto) {
        User user =  User
                .builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    userRepository.save(user);
    }

    @Override
    public UpdateResponse updateUser(UpdateRequest req , MultipartFile img) {
        UUID userId = req.getId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            String folder = "profile_pictures";
            Map data =  cloudinaryImgService.upload(img,folder);
            String imageUrl = (String) data.get("secure_url");
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
            user.setTargetWeight(req.getTargetWeight());
            user.setImageName(imageUrl);

            User savedUser = userRepository.save(user);
            return  UpdateResponse.builder()
                    .user(savedUser)
                    .message("User updated successfully...")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
        return UpdateResponse.builder()
                .message("User not found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }
}