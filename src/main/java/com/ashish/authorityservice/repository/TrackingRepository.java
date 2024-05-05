package com.ashish.authorityservice.repository;

import com.ashish.authorityservice.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,Long> {

    Optional<Tracking> findByUserId(UUID userId);
    Optional<Tracking> findFirstByUserIdOrderByTrackingDateDesc(UUID userId);
    @Query("SELECT t FROM Tracking t WHERE t.userId = :userId AND t.trackingDate BETWEEN :startDate AND :endDate")
    List<Tracking> findByUserIdAndDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
