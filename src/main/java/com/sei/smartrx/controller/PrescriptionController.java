package com.sei.smartrx.controller;

import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello World";
    }

    @GetMapping(path = "/prescriptions")
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        if (prescriptionList.isEmpty()) {

        }
    }

}
