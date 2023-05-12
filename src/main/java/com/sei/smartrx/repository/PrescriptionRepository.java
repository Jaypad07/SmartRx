package com.sei.smartrx.repository;

import com.sei.smartrx.models.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<List<Prescription>> findByUserId(Long userId);
}

//    List<Prescription> getPrescriptionsByUserId(Long userId);