package com.ashish.authorityservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryImgService {
    Map upload(MultipartFile file,String folder);
}
