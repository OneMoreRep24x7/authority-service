package com.ashish.authorityservice.configuartion;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name","dtwgcoyn5");
        config.put("api_key","267249849153685");
        config.put("api_secret","Nb_YU7M8uBSGD3WLYE3moiFkXjc");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
