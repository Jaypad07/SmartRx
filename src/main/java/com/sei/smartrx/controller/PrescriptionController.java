package com.sei.smartrx.controller;

import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello World";
    }

    /**
     * Will need this URL for pharmacist later
     * @return
     */
    @GetMapping(path = "/prescriptions")
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found.");
        }else return prescriptionList;
    }

//    /**
//     * based on given userId, returns list of Prescriptions that userId = prescription.user_id
//     * @param userId
//     * @return List of Prescriptions
//     */
//    @GetMapping(path = "/prescriptions/{userId}")
//    public List<Prescription> getAllPrescriptionsForUser(@PathVariable Long userId)  {
//        List<Prescription> prescriptionList = prescriptionRepository.getPrescriptionsByUserId(userId);
//        System.out.println(prescriptionList);
//        if (prescriptionList.size() == 0) {
//            throw new InformationNotFoundException("No previous prescriptions found.");
//        } else return prescriptionList;
//    }

    /**
     * based on given userId, returns list of Prescriptions that userId = prescription.user_id
     *
     * @param userId
     * @return list of prescriptions
     */
    @GetMapping(path = "/prescriptions/{userId}")
    public ResponseEntity<?> getAllPrescriptionsForUser(@PathVariable Long userId) {
        Optional<List<Prescription>> prescriptionList = prescriptionRepository.findByUserId(userId);
        if (prescriptionList.isPresent()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("data", prescriptionList.get());
            data.put("user", userId);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            throw new InformationNotFoundException("No previous prescriptions found.");
        }
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
}
