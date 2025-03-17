package com.challenge.apicalculator.repository;

import com.challenge.apicalculator.model.ApiCallHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ApiCallHistoryRepository extends JpaRepository<ApiCallHistory, Long> {

    Page<ApiCallHistory> findAllByOrderByTimestampDesc(Pageable pageable);
    
    Page<ApiCallHistory> findByEndpointContainingOrderByTimestampDesc(String endpoint, Pageable pageable);
    
    Page<ApiCallHistory> findByTimestampBetweenOrderByTimestampDesc(
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
} 