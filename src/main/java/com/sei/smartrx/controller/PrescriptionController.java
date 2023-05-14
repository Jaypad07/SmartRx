package com.sei.smartrx.controller;

import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    /**
     * Sets the PrescriptionService instance for this PrescriptionManager.
     * This method is used for dependency injection to provide the necessary service implementation.
     * @param prescriptionService The PrescriptionService instance to be injected.
     */
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
     * @param prescriptionId prescriptionId
     * @return Prescription the user has requested to be refilled with updates refill #
     */
    @GetMapping(path="/prescriptions/newRequest/{prescriptionId}")
    public Prescription requestPrescriptionRefill(@PathVariable Long prescriptionId){
        return prescriptionService.requestPrescriptionRefill(prescriptionId);
    }

    /**
     * GET: endpoint http://localhost:8080/api/prescriptions/medications/1
     * GET ONE MEDICATION, gets a medication by its ID.
     * @param medicationId medicationID
     * @return a Medication object
     */
    @GetMapping(path="/prescriptions/medications/{medicationId}")
    public Medication getAMedication(@PathVariable Long medicationId){
        return prescriptionService.getAMedication(medicationId);
    }

    /**
     * GET: endpoint http://localhost:8080/api/pharmacist/prescriptions
     * GET ALL PRESCRIPTIONS, must have pharmacist userprofile role to access this through user service.
     * @return a list of all prescriptions
     */
    @GetMapping (path="/pharmacist/prescriptions")
    public List<Prescription> allPrescriptions(){
        return prescriptionService.getAllPrescriptions();
    }

    /**
     * PUT: endpoint http://localhost:8080/api/pharmacist/prescriptions/1
     * UPDATE A SPECIFIC PRESCRIPTION, must have pharmacist userprofile role to access this through user service.
     * @param prescriptionId
     * @param prescriptionObject
     * @return an instance of the updated prescription
     */
    @PutMapping(path="/pharmacist/prescriptions/{prescriptionId}")
    public Prescription updatePrescription(@PathVariable Long prescriptionId, @RequestBody Prescription prescriptionObject) {
        return prescriptionService.updatePrescription(prescriptionObject, prescriptionId);
    }

    /**
     * GET: endpoint http://localhost:8080/api/pharmacist/prescriptions/1
     * GET A SPECIFIC PRESCRIPTION BY ID. Must be a pharmacist to see any prescription, verifies in prescription service.
     * @param prescriptionId Long
     * @return Prescription
     */
    @GetMapping (path="/pharmacist/prescriptions/{prescriptionId}")
    public Prescription getAPrescriptionId(@PathVariable Long prescriptionId){
        return prescriptionService.getAPrescriptionsById(prescriptionId);
    }

    /**
     * POST: endpoint http://localhost:8080/api/pharmacist/prescriptions/1?ids=1,2,3
     * The @PathVariable annotation is used to retrieve the userId from the path,
     * and the @RequestParam annotation is used to retrieve the ids request parameter, which represents a list of medication IDs.
     * @param userId The ID of the user for whom the prescriptions are being updated.
     * @param medicationIds A list of medication IDs associated with the prescriptions.
     * @param prescriptionObject An object representing the updated prescription's data.
     * @return a prescription filled with a set user and medication List
     */
    @PostMapping(path = "/pharmacist/prescriptions/{userId}")
    public Prescription createPrescriptionForUser(@PathVariable Long userId, @RequestParam("ids") List<Long> medicationIds, @RequestBody Prescription prescriptionObject) {
        return prescriptionService.createPrescriptionForUser(userId, medicationIds, prescriptionObject);
    }

    /**
     * DELETE: endpoint http://localhost:8080/api/pharmacist/prescriptions/1
     * Deletes a specific prescription by ID. Must have Pharmacist role.
     * @param prescriptionId
     * @return Nothing
     */
    @DeleteMapping(path = "/pharmacist/prescriptions/{prescriptionId}")
    public Prescription deletePrescription(@PathVariable Long prescriptionId ) {
        return prescriptionService.deletePrescription(prescriptionId);
    }
}
