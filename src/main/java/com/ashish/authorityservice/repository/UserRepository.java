package com.ashish.authorityservice.repository;

import com.ashish.authorityservice.model.Trainer;
import com.ashish.authorityservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


}
