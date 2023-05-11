package com.sei.smartrx.repository;

import com.sei.smartrx.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
