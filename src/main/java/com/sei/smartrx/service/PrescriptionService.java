package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.MedicationRepository;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicationRepository medicationRepository;


    /**
     * Sets the MedicationRepository dependency.
     *
     * @param medicationRepository the MedicationRepository
     */
    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    /**
     * sets the PrescriptionRepository dependency.
     *
     * @param prescriptionRepository the PrescriptionRepository
     */

    @Autowired
    public void setPrescriptionRepository(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }
    //---------------------------------------------

    /**
     * Retrives the current logged-in user
     *
     * @return the User instance representing the current logged-in user
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    //---------------------------------------------

    /**
     * Retrives all prescriptions
     *
     * @return a list of all prescriptions
     * @throws InformationNotFoundException if no previous prescriptions are found
     */

    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found.");
        }else return prescriptionList;
    }

    /**
     * Retrieves all prescriptions for the current logged-in user
     *
     * @return a list of prescriptions for the current user
     * @throws InformationNotFoundException if no previous prescriptions are found for the current user
     */

    public List<Prescription> getAllPrescriptionsForUser() {
        List<Prescription> prescriptionList = prescriptionRepository.findByUserId(getCurrentLoggedInUser().getId()).get();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found for user with id of " + getCurrentLoggedInUser().getId());
        } else return prescriptionList;
    }

    /**
     * Retrieves a medication by its ID.
     *
     * @param medicationId the ID of the medication to retrieve
     * @return the Medication instance corresponding to the provided ID
     * @throws InformationNotFoundException if now medication is found with the provided ID
     */

    public Medication seeAMedication(Long medicationId){
        Optional<Medication> medication = medicationRepository.findById(medicationId);
        if(medication.isEmpty()){
            throw new InformationNotFoundException("There is no medication with id of " + medicationId);
        }
        else{
            return medication.get();
        }
    }

    /**
     * Requests a prescription refill by its ID
     * @param prescriptionId the ID of the prescription to refill
     * @return the Prescription instance corresponding to the ID provided
     * @throws InformationNotFoundException if no prescription is found with the provided ID
     */

    public Prescription requestPrescriptionRefill(Long prescriptionId){
        Optional<Prescription> refillPrescription = Optional.of(prescriptionRepository.getById(prescriptionId));
        if(refillPrescription.isEmpty()){
            throw new InformationNotFoundException("There is no prescription with this id present");
        }
        else{
            return refillPrescription.get();
        }
    }



}
