package com.ashish.authorityservice.controller;

import com.ashish.authorityservice.service.CloudinaryImgService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary/upload")
public class CloudinaryImageUploadController {
    @Autowired
    CloudinaryImgService cloudinaryImgService;

    @PostMapping
    public ResponseEntity<Map> uploadImage(@RequestParam("image")MultipartFile file){
        String folder = "Certificates";
        Map data = cloudinaryImgService.upload(file,folder);
        return ResponseEntity.ok(data);
    }
}
