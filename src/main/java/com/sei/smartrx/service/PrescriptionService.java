package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.exceptions.PrescriptionNotFoundException;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PrescriptionService {
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Autowired
    public void setPrescriptionRepository(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }
    //---------------------------------------------
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    //---------------------------------------------

    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found.");
        }else return prescriptionList;
    }

    public List<Prescription> getAllPrescriptionsForUser() {
        List<Prescription> prescriptionList = prescriptionRepository.findByUserId(getCurrentLoggedInUser().getId()).get();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found for user with id of " + getCurrentLoggedInUser().getId());
        } else return prescriptionList;
    }

    public Medication seeAMedication(Long medicationId){
        Optional<Medication> medication = medicationRepository.findById(medicationId);
        if(medication.isEmpty()){
            throw new InformationNotFoundException("There is no medication with id of " + medicationId);
        }
        else{
            return medication.get();
        }
    }

    public Prescription requestPrescriptionRefill(Long prescriptionId){
        Optional<Prescription> refillPrescription = Optional.of(prescriptionRepository.getById(prescriptionId));
        if(refillPrescription.isEmpty()){
            throw new InformationNotFoundException("There is no prescription with this id present");
        }
        else{
            return refillPrescription.get();
        }
    }


    /**
     * this method allows a pharmacist to update a prescription.
     * the method first checks if if the prescription exists in the database.
     * if the prescription is found, the prescription fields will be updated with
     * values from the prescription object.
     * a PrescriptionNotFoundException thrown if prescription not found
     * @param prescriptionObject
     * @return prescriptionObject
     */
    public Prescription updatePrescription(Prescription prescriptionObject, Long prescriptionId) {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isPresent()) {
            Prescription existingPrescription = prescription.get();
            existingPrescription.setPatientName(prescriptionObject.getPatientName());
            existingPrescription.setRefills(prescriptionObject.getRefills());
            existingPrescription.setEndDate(prescriptionObject.getEndDate());
            existingPrescription.setStatus(prescriptionObject.getStatus());
            prescriptionRepository.save(existingPrescription); //need to save the object by using .save method
            return existingPrescription;
        } else {  //custom exception thrown if prescription is not found in the database
            throw new PrescriptionNotFoundException("Prescription:" + id + "could not be found. " + prescriptionObject.getPrescriptionId());

        }
    }






    }


