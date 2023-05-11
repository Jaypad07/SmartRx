package com.sei.smartrx.controller;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

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
//     * @param userId
     * @return List of Prescriptions
     */
    // http://localhost:8080/api/prescriptions/1
//    @GetMapping(path = "/prescriptions/{userId}")
//    public List<Prescription> getAllPrescriptionsForUser(@PathVariable Long userId)  {
//        return prescriptionService.getAllPrescriptionsForUser(userId);
//    }

    // http://localhost:8080/api/newRequest/prescriptions/1
    @GetMapping(path="/prescriptions/newRequest/{prescriptionId}")
    public Prescription requestPrescriptionRefill(@PathVariable Long prescriptionId){
        return prescriptionService.requestPrescriptionRefill(prescriptionId);
    }

    // http://localhost:8080/api/prescriptions/medications/1
    @GetMapping(path="/prescriptions/medications/{medicationId}")
    public Medication seeAMedication(@PathVariable Long medicationId){
        return prescriptionService.seeAMedication(medicationId);
    }
}
