package com.ashish.authorityservice.service;

import com.ashish.authorityservice.dto.UpdateResponse;
import com.ashish.authorityservice.dto.UpdateRequest;
import com.ashish.authorityservice.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void registerUser(UserDto userDto);

    UpdateResponse updateUser(UpdateRequest req, MultipartFile image);
}
