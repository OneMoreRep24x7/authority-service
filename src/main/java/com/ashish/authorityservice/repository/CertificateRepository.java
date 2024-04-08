package com.ashish.authorityservice.repository;

import com.ashish.authorityservice.model.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificates,Long> {
}
