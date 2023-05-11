package com.sei.smartrx.repository;

import com.sei.smartrx.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> getPrescriptionsByUserId(Long userId);
}
