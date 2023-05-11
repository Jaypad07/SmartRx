package com.sei.smartrx.controller;

import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.MedicationRepository;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    private PrescriptionService prescriptionService;

    @Autowired
    public void setPrescriptionService(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * Will need this URL for pharmacist later
     * @return
     */
    // http://localhost:8080/api/prescriptions
    @GetMapping(path = "/prescriptions")
    public List<Prescription> getAllPrescriptions() {
      return prescriptionService.getAllPrescriptions();
    }

    /**
     * based on given userId, returns list of Prescriptions that userId = prescription.user_id
     * @param userId
     * @return List of Prescriptions
     */
    @GetMapping(path = "/prescriptions/{userId}")
    public List<Prescription> getAllPrescriptionsForUser(@PathVariable Long userId)  {
        List<Prescription> prescriptionList = prescriptionRepository.findByUserId(userId).get();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found.");
        } else return prescriptionList;
    }

    @GetMapping(path="/prescriptions/newRequest/{prescriptionId}")
    public Prescription requestPrescriptionRefill(@PathVariable Long prescriptionId){
        Optional<Prescription> refillPrescription = Optional.of(prescriptionRepository.getById(prescriptionId));
        if(refillPrescription.isEmpty()){
            throw new InformationNotFoundException("There is no prescription with this id present");
        }
        else{
            return refillPrescription.get();
        }
    }

    @GetMapping(path="/prescriptions/medications/{medicationId}")
    public Medication seeAMedication(@PathVariable Long medicationId){
        Optional<Medication> medication = medicationRepository.findById(medicationId);
        if(medication.isEmpty()){
            throw new InformationNotFoundException("There is no medication with id of " + medicationId);
        }
        else{
            return medication.get();
        }
    }
}
