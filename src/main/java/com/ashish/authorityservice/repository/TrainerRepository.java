package com.ashish.authorityservice.repository;

import com.ashish.authorityservice.model.Certificates;
import com.ashish.authorityservice.model.Trainer;
import com.ashish.authorityservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, UUID> {
    @Query("SELECT t.certificates FROM Trainer t WHERE t.id = :trainerId")
    List<Certificates> findCertificatesByTrainerId(@Param("trainerId") UUID trainerId);




}
