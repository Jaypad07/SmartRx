package com.sei.smartrx.seed;

import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionDataLoader implements CommandLineRunner {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Override
    public void run(String...args) throws Exception{
        loadPrescriptionData();
    }

    private void loadPrescriptionData(){
        if(prescriptionRepository.count() == 0){
            Prescription prescription1 = new Prescription("John Beck", "Lisinipril", 20.0, 30, "Twice a day", "By mouth", 3, 2023/08/01, true);
            Prescription prescription2 = new Prescription("John Beck", "Atorvastatin", 50.0, 30, "Once at night", "By mouth", 2, 2023/07/01, true);
            Prescription prescription3 = new Prescription("Joan Hill", "Amlodipine", 5.0, 30, "Once in morning", "By mouth", 3, 2023/08/01, true);
            prescriptionRepository.save(prescription1);
            prescriptionRepository.save(prescription2);
            prescriptionRepository.save(prescription3);
        }
    }
}
