package com.sei.smartrx.seed;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.MedicationRepository;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.repository.UserRepository;
import com.sei.smartrx.service.UserService;
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


    private PrescriptionRepository prescriptionRepository;
    @Autowired
    public void setPrescriptionRepository(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    private MedicationRepository medicationRepository;

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private UserService userService;

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
            Prescription prescription4 = new Prescription("Another name", 6, currentDate, true);
            Prescription prescription5 = new Prescription("Patient A", 0, currentDate, false);

            User user = new User("Stacey", "Smith", "email@email.com", currentDate, "password", "watermelon");
            User user2 = new User("Mike", "Harrington", "email1@email.com", currentDate, "password1", "seafood, iodine");
            User user3 = new User("Bill", "Brown", "email2@email.com", currentDate, "password2", "latex, zinc, peanuts");
            Medication medication1 = new Medication(1L, "name", "genName", "contra", "sideEffects", "ingred");
            medicationRepository.save(medication1);

            // user can have many prescriptions
            List<Prescription> prescriptionList = new ArrayList<>();
            prescriptionList.add(prescription1);
            prescriptionList.add(prescription2);
            prescriptionList.add(prescription3);
            List<Prescription> prescriptionList2 = new ArrayList<>();
            prescriptionList2.add(prescription4);
            prescriptionList2.add(prescription5);
            // reference each prescription to user
            prescription1.setUser(user);
            prescription2.setUser(user);
            prescription3.setUser(user);
            prescription4.setUser(user2);
            prescription1.setUser(user3);
            prescription5.setUser(user2);
            // many prescriptions belong to one user
            user.setPrescriptionList(prescriptionList);
            user2.setPrescriptionList(prescriptionList2);
            // save data
            userService.createUser(user);
            userService.createUser(user2);
            userService.createUser(user3);
            prescriptionRepository.saveAll(prescriptionList);
            prescriptionRepository.saveAll(prescriptionList2);
            System.out.println(user.getPrescriptionList());
        }
    }
}