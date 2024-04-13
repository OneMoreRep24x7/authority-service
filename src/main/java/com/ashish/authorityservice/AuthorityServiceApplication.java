package com.ashish.authorityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthorityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorityServiceApplication.class, args);
	}

}
