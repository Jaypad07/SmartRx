package com.sei.smartrx.controller;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    @Autowired
    public void setPrescriptionService(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * GET: endpoint http://localhost:8080/api/prescriptions
     * @return List of Prescriptions for a specific user
     */

    @GetMapping(path = "/prescriptions")
    public List<Prescription> getAllPrescriptionsForUser()  {
        return prescriptionService.getAllPrescriptionsForUser();
    }

    /**
     * GET: endpoint http://localhost:8080/api/newRequest/prescriptions/1
     * @return Prescription the user has requested to be refilled with updates refill #
     */
    @GetMapping(path="/prescriptions/newRequest/{prescriptionId}")
    public Prescription requestPrescriptionRefill(@PathVariable Long prescriptionId){
        return prescriptionService.requestPrescriptionRefill(prescriptionId);
    }

    /**
     * GET: endpoint http://localhost:8080/api/prescriptions/medications/1
     * @param medicationID
     * @return a Medication object
     */
    @GetMapping(path="/prescriptions/medications/{medicationId}")
    public Medication seeAMedication(@PathVariable Long medicationId){
        return prescriptionService.seeAMedication(medicationId);
    }

@DeleteMapping(path = "/prescriptions/{prescriptionId}")
    public void deletePrescription(@PathVariable Long prescriptionId ) {
        // Implement the logic to delete the prescription
        return prescriptionService.delete(prescriptionId);
    }
}
