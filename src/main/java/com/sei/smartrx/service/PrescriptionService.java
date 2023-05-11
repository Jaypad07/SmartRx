package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    public void setPrescriptionRepository(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        if (prescriptionList.size() == 0) {
            throw new InformationNotFoundException("No previous prescriptions found.");
        }else return prescriptionList;
    }
}
