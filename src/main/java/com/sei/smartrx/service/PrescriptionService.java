package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.exceptions.PrescriptionNotFoundException;
import com.sei.smartrx.exceptions.NoAuthorizationException;
import com.sei.smartrx.models.Medication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.models.UserProfile;
import com.sei.smartrx.repository.MedicationRepository;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.repository.UserRepository;
import com.sei.smartrx.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    private PrescriptionRepository prescriptionRepository;

    private MedicationRepository medicationRepository;

    private UserRepository userRepository;

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Autowired
    public void setPrescriptionRepository(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //---------------------------------------------
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    //---------------------------------------------

    public List<Prescription> getAllPrescriptions() {
        Optional<UserProfile> userProfile = Optional.ofNullable(getCurrentLoggedInUser().getUserProfile());
        if(userProfile.isPresent() && userProfile.get().getRole().equals("ROLE_PHARMACIST")){
            List<Prescription> prescriptionList = prescriptionRepository.findAll();
            if (prescriptionList.size() == 0) {
                throw new InformationNotFoundException("No previous prescriptions found.");
            } else return prescriptionList;
        }
        else{
            throw new NoAuthorizationException("You are not authorized to pull all prescriptions");
        }
    }

    public List<Prescription> getAllPrescriptionsForUser() {
        Optional<List<Prescription>> prescriptionList = prescriptionRepository.findByUserId(getCurrentLoggedInUser().getId());
            if (prescriptionList.isPresent()) {
                if (prescriptionList.get().size() == 0) {
                    throw new InformationNotFoundException("No previous prescriptions found for user with id of " + getCurrentLoggedInUser().getId());
                }else return prescriptionList.get();
            }else throw new InformationNotFoundException("Could not find a list for current logged in user.");
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
        Optional<Prescription> refillPrescription = prescriptionRepository.findById(prescriptionId);
        if(refillPrescription.isEmpty()){
            throw new InformationNotFoundException("There is no prescription with this id present");
        }
        else{
            return refillPrescription.get();
        }
    }

    public Prescription getAPrescriptionsById(Long prescriptionId) {
        Optional<UserProfile> userProfile = Optional.ofNullable(getCurrentLoggedInUser().getUserProfile());
        if (userProfile.isPresent() && userProfile.get().getRole().equals("ROLE_PHARMACIST")) {
            Optional<Prescription> onePrescription = prescriptionRepository.findById(prescriptionId);
            if (onePrescription.isEmpty()) {
                throw new InformationNotFoundException("There is no prescription with this id present");
            } else {
                return onePrescription.get();
            }
        } else{
            throw new NoAuthorizationException("Not authorized to view this prescription");
        }
    }
    public Prescription createPrescriptionForUser(Long prescriptionId, Long userId, Prescription prescriptionObject) {
        Optional<UserProfile> userProfile = Optional.ofNullable(getCurrentLoggedInUser().getUserProfile());
        if (userProfile.isPresent() && userProfile.get().getRole().equals("ROLE_PHARMACIST")) {
            Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
            if (prescription.isPresent()) {
                throw new InformationExistException("Prescription with " + prescriptionId + "already exists.");
            } else {
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    prescriptionObject.setUser(user.get());
                    prescriptionObject.setPatientName(user.get().getFirstName() + " " + user.get().getLastName());
                    prescriptionObject.setRefills(prescriptionObject.getRefills());
                    prescriptionObject.setEndDate(prescriptionObject.getEndDate());
                    prescriptionObject.setStatus(prescriptionObject.getStatus());
                    prescriptionObject.setMedicationList(prescriptionObject.getMedicationList()); //May delete for medication method.
                    return prescriptionRepository.save(prescriptionObject);
                } else throw new InformationNotFoundException("User with ID " + userId + " does not exist.");
            }
        } else throw new NoAuthorizationException("Not authorized to create a prescription");
    }///build a method that sets medication to prescription list. THen another that sets prescription list to medication

    public Prescription deletePrescription(Long prescriptionId) {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isEmpty()) {
            throw new InformationNotFoundException("Prescription with " + prescriptionId + " does not exist.");
        }else {
            prescriptionRepository.deleteById(prescriptionId);
            return prescription.get();
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
        Optional<UserProfile> userProfile = Optional.ofNullable(getCurrentLoggedInUser().getUserProfile());
        if (userProfile.isPresent() && userProfile.get().getRole().equals("ROLE_PHARMACIST")) {
            Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
            if (prescription.isPresent()) {
                Prescription existingPrescription = prescription.get();
                existingPrescription.setPatientName(prescriptionObject.getPatientName());
                existingPrescription.setRefills(prescriptionObject.getRefills());
                existingPrescription.setEndDate(prescriptionObject.getEndDate());
                existingPrescription.setStatus(prescriptionObject.getStatus());
                prescriptionRepository.save(existingPrescription);
                return existingPrescription;
            } else {
                throw new PrescriptionNotFoundException("Prescription with id " + prescriptionId + " not found.");
            }
        } else {
            throw new NoAuthorizationException("User not authorized to update prescription.");
        }
      }
   }
