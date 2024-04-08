package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.UserAddResponse;
import com.ashish.authorityservice.dto.UserAddRequest;
import com.ashish.authorityservice.dto.UserDto;
import com.ashish.authorityservice.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    void registerUser(UserDto userDto);

    UserAddResponse addUser(UserAddRequest req, MultipartFile image);

    User getUserDetails(UUID userId);
}
