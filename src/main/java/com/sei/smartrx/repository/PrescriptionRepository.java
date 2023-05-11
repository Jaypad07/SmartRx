package com.sei.smartrx.repository;

import com.sei.smartrx.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<List<Prescription>> findByUserId(Long userId);
}

//    List<Prescription> getPrescriptionsByUserId(Long userId);