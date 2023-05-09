package com.sei.SmartRx.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="prescriptions")
public class Prescription {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long prescriptionId;

    @Column
    String patientName;

    @Column
    String medicationName;

    @Column
    Double doseage;

    @Column
    int quantity;

    @Column
    String frequency;

    @Column
    String route;

    @Column
    int refills;

    @Column
    LocalDate endDate;

    @Column
    Boolean status;
    /**
     * Create both an empty default constructor and a loaded constructor
     */
    public Prescription() {}

    public Prescription(Long prescriptionId, String patientName, String medicationName, Double doseage, int quantity, String frequency, String route, int refills, LocalDate endDate, Boolean status) {
        this.prescriptionId = prescriptionId;
        this.patientName = patientName;
        this.medicationName = medicationName;
        this.doseage = doseage;
        this.quantity = quantity;
        this.frequency = frequency;
        this.route = route;
        this.refills = refills;
        this.endDate = endDate;
        this.status = status;
    }

}
