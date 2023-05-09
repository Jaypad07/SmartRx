package com.sei.SmartRx.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="prescriptions")
public class Prescription {
    @Column
    @Id

    Long prescriptionId;
    String patientName;
    String medicationName;
    Double doseage;
    int quantity;
    String frequency;
    String route;
    int refills;
    LocalDate endDate;
    Boolean status;

}
