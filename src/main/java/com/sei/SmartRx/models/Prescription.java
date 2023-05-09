package com.sei.SmartRx.models;

import java.time.LocalDate;

public class Prescription {
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
