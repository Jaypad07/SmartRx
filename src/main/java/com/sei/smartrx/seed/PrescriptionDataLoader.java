package com.sei.smartrx.seed;

import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class PrescriptionDataLoader implements CommandLineRunner {

    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Override
    public void run(String... args) throws Exception{
        loadPrescriptionData();
    }

    private void loadPrescriptionData(){
        if(prescriptionRepository.count() == 0) {
            Prescription prescription = new Prescription(1L, "John Beck", 5, currentDate, true);
            prescriptionRepository.save(prescription);
        }
    }
}
