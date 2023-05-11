package com.sei.smartrx.seed;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.MedicationRepository;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PrescriptionDataLoader implements CommandLineRunner {

    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        loadPrescriptionData();
    }

    private void loadPrescriptionData() {

        System.out.println("Calling PrescriptionDataLoader");

        if (prescriptionRepository.count() == 0) {
            Prescription prescription1 = new Prescription("John Beck", 5, currentDate, true);
            Prescription prescription2 = new Prescription("Joan Hill", 2, currentDate, true);
            Prescription prescription3 = new Prescription("Eric Slack", 2, currentDate, false);

            User user = new User("Stacey", "Smith", "email@email.com", currentDate, "password", "watermelon");
            Medication medication1 = new Medication(1L, "name", "genName", "contra", "sideEffects", "ingred");
            medicationRepository.save(medication1);

            // user can have many prescriptions
            List<Prescription> prescriptionList = new ArrayList<>();
            prescriptionList.add(prescription1);
            prescriptionList.add(prescription2);
            prescriptionList.add(prescription3);

            // reference each prescription to user
            prescription1.setUser(user);
            prescription2.setUser(user);
            prescription3.setUser(user);

            // many prescriptions belong to one user
            user.setPrescriptionList(prescriptionList);

            // save data
            userRepository.save(user);
            prescriptionRepository.saveAll(prescriptionList);

            System.out.println(user.getPrescriptionList());
        }
    }
}