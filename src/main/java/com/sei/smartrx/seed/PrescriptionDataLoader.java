package com.sei.smartrx.seed;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.models.UserProfile;
import com.sei.smartrx.repository.MedicationRepository;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.repository.UserRepository;
import com.sei.smartrx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PrescriptionDataLoader implements CommandLineRunner {

    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();


    private PrescriptionRepository prescriptionRepository;

    /**
     * Sets the MedicationRepository dependency.
     *
     * @param prescriptionRepository the MedicationRepository instance to be injected
     */
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

    /**
     * Sets the UserRepository dependency.
     *
     * @param userRepository the UserRepository instance to be injected
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private UserService userService;

    /**
     * Runds the data loading process
     * @param args the command line arguments
     * @throws Exception if an error occurs during the data loading process
     */
    @Override
    public void run(String... args) throws Exception {
        loadPrescriptionData();
    }

    /**
     * Loads prescription data into the application
     */

    private void loadPrescriptionData() {

        System.out.println("Calling PrescriptionDataLoader");

        if (prescriptionRepository.count() == 0) {
            Prescription prescription1 = new Prescription("Stacey Smith", 5, currentDate, true);
            Prescription prescription2 = new Prescription("Stacey Smith", 2, currentDate, true);
            Prescription prescription3 = new Prescription("Mike Harrington", 2, currentDate, false);
            Prescription prescription4 = new Prescription("Bill Brown", 6, currentDate, true);
            Prescription prescription5 = new Prescription("Stacey Smith", 0, currentDate, false);
            Prescription prescription6 = new Prescription("Bill Brown", 2, currentDate, true);

            Medication medication1 = new Medication(1L, "Metformin", "metformin hydrochloride", "severe kidney impairment, radiographic contrast dye, respiratory insufficiency", "gastrointestinal issues such as nausea and vomiting, vitamin b12 deficiency, and loss of appetite", "metformin hydrochloride, povidone, magnesium stearate, sodium starch glycolate");
            Medication medication2 = new Medication(2L, "Glimepiride", "glimepiride", "Type 1 diabetes, pregnancy or breastfeeding, liver impairment", "hypoglycemia, photosensitivity, weight gain", "glimepiride, lactose monohydrate, cellulose, Sodium starch glycolate ");
            Medication medication3 = new Medication(3L, "Zestril", "lisinipril", "pregnancy, simultaneous use with sacubitril/valsartan, renal artery stenosis", "cough, dizziness, low blood pressure", "calcium phosphate, magnesium stearate, mannitol, corn starch");
            Medication medication4 = new Medication (4L, "Entresto", "sacubitril/valsartan", "simultaneous use of lisinipril, severe liver impairment, taking any MAO inhibitors", "low blood pressure, high potassium blood levels, increased heart rate, fatigue and/or weakness", "sacubitril, valsartan");
            Medication medication5 = new Medication(5L, "Rheumatrex", "methotrexate", "breastfeeding, sever liver disease, pre-existing blood disorders", "loss of appetite, stomach pain, diarrhea, skin rash, hair loss, mouth sores", "methotrexate, tablet binder ingredients vary with manufacturer: consult specific manufacturer for list of binding ingredients");
            Medication medication6 = new Medication(6L, "Coumadin", "warfarin sodium", "bleeding disorders, recent surgery or trauma, uncontrolled high blood pressure", "blood in urine, gum bleeding, increased bruising and bleeding, upset stomach or abdominal pain", "warfarin sodium, binding ingredients vary by manufacturer");
            Medication medication7 = new Medication(7L, "Tenormin", "Atenolol", "heart rhythm disorders, Asthma or chronic obstructive pulmonary disease, and/or heart failure", "Fatigue or tiredness, cold hands and feet, slow heart rate, difficulty sleeping", "atenolol");
            Medication medication8 = new Medication(8L, "Prilosec", "omeprazole",  "severe liver disease, osteoporosis, low magnesium levels, or vitamin B12 deficiency", "headache, constipation, Muscle pain or weakness", "omeprazole" );
            Medication medication9 = new Medication (9L, "Zoloft", "sertraline", "Use of monoamine oxidase inhibitors (MAOIs), used with caution during pregnancy and breastfeeding, liver or kidney impairment, epilepsy, bipolar disorder, or a history of bleeding disorders", "Dizziness or lightheadedness, sweating, Insomnia or sleep disturbances", "sertraline hydrochloride");
            Medication medication10 = new Medication(10L, "Lexapro", " Escitalopram", "liver or kidney impairment, epilepsy, bipolar disorder, or a history of bleeding disorders", "weight changes, nausea, dry mouth, drowsiness and fatigue", "escitalopram oxalate");

            User user = new User("Stacey", "Smith", "email@email.com", currentDate, "password", "watermelon");
            User user2 = new User("Mike", "Harrington", "email1@email.com", currentDate, "password1", "seafood, iodine");
            User user3 = new User("Bill", "Brown", "email2@email.com", currentDate, "password2", "latex, zinc, peanuts");
            User user4 = new User("Crosby", " Marsh", "pharmacist@cvs.com", currentDate, "password", "NKDA");

            //creating authorized pharmacist profile, setting user 4 userProfile to pharmacist.
            UserProfile pharmacistProfile = new UserProfile("ROLE_PHARMACIST");
            user4.setUserProfile(pharmacistProfile);

            // each user is linked to each prescription
            prescription1.setUser(user);
            prescription2.setUser(user);
            prescription5.setUser(user);
            prescription3.setUser(user2);
            prescription5.setUser(user3);

            // user can have many prescriptions, this adds each prescription object to a list
            List<Prescription> prescriptionList = new ArrayList<>();
            prescriptionList.add(prescription1);
            prescriptionList.add(prescription2);
            prescriptionList.add(prescription5);
            List<Prescription> prescriptionList2 = new ArrayList<>();
            prescriptionList2.add(prescription3);
            List<Prescription> prescriptionList3 = new ArrayList<>();
            prescriptionList2.add(prescription4);
            prescriptionList2.add(prescription6);

            // adding the list of many prescriptions to the user
            user.setPrescriptionList(prescriptionList);
            user2.setPrescriptionList(prescriptionList2);
            user3.setPrescriptionList(prescriptionList3);
            //saving all Medication data
            medicationRepository.save(medication1);
            medicationRepository.save(medication2);
            medicationRepository.save(medication3);
            medicationRepository.save(medication4);
            medicationRepository.save(medication5);
            medicationRepository.save(medication6);
            medicationRepository.save(medication7);
            medicationRepository.save(medication8);
            medicationRepository.save(medication9);
            medicationRepository.save(medication10);
            //many medications belong to many prescriptions
            List<Medication> medicationsForPrescription2 = new ArrayList<>();
            medicationsForPrescription2.add(medication1);
            medicationsForPrescription2.add(medication6);
            medicationsForPrescription2.add(medication7);
            medicationsForPrescription2.add(medication8);
            medicationsForPrescription2.add(medication10);
            List<Medication> medicationsForPrescription1 = new ArrayList<>();
            medicationsForPrescription1.add(medication2);
            medicationsForPrescription1.add(medication5);
            medicationsForPrescription1.add(medication6);
            medicationsForPrescription1.add(medication8);
            List<Medication> medicationsForPrescription3 = new ArrayList<>();
            medicationsForPrescription3.add(medication9);
            //set the list of medications to the prescription object
            prescription1.setMedicationList(medicationsForPrescription1);
            prescription4.setMedicationList(medicationsForPrescription2);
            prescription3.setMedicationList(medicationsForPrescription3);

            // save data
            userService.registerUser(user);
            userService.registerUser(user2);
            userService.registerUser(user3);
            userService.registerUser(user4);
            prescriptionRepository.saveAll(prescriptionList);
            prescriptionRepository.saveAll(prescriptionList2);
        }
    }
}