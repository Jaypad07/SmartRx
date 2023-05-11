package com.sei.smartrx.seed;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PrescriptionDataLoader implements CommandLineRunner {

    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        loadPrescriptionData();
    }

    private void loadPrescriptionData() {
        ArrayList<Prescription> arrayList = new ArrayList<>();

        if (prescriptionRepository.count() == 0) {
            Prescription prescription1 = new Prescription(1L, "John Beck", 5, currentDate, true);
            Prescription prescription2 = new Prescription(2L, "Joan Hill", 2, currentDate, true);
            Prescription prescription3 = new Prescription(3L, "Eric Slack", 2, currentDate, false);
            User user1 = new User(1L, "Stacey", "Smith", "email@email.com", currentDate, "password", "watermelon");
//            User user1 = userRepository.findById(1L).get();
            user1.setPrescriptionList(Arrays.asList(prescription1, prescription2, prescription3));
            prescription1.setUser(user1);
            prescription2.setUser(user1);
            prescription3.setUser(user1);
            prescriptionRepository.save(prescription1);
            prescriptionRepository.save(prescription2);
            prescriptionRepository.save(prescription3);
            userRepository.save(user1);
            Medication med1 = new Medication(1L, "Ceftriaxone", "Rocephin", "poor kidney function", "pain at site, rash, loss of appetitie", "sodium chloride, ceftriaxone");
//            User user1 = new User(1L, "Stacey", "Smith", "email@email.com", currentDate, "password", "watermelon");
            arrayList.add(prescription1);
            arrayList.add(prescription2);
            user1.setPrescriptionList(arrayList);
            userRepository.save(user1);
        }
    }
}
