package com.sei.SmartRx.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    /**
     * Created Many-to-Many relationship to Medication
     * joined by column "prescription_medication, where that id is equal to medication ID
     */
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "prescription_medication", joinColumns = @JoinColumn(name = "prescription_id"), inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private List<Medication> medicationList;

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    /**
     * getters and setters for Prescription attributes and "TO STRING" method for readability
     */
    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Double getDoseage() {
        return doseage;
    }

    public void setDoseage(Double doseage) {
        this.doseage = doseage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getRefills() {
        return refills;
    }

    public void setRefills(int refills) {
        this.refills = refills;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId=" + prescriptionId +
                ", patientName='" + patientName + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", doseage=" + doseage +
                ", quantity=" + quantity +
                ", frequency='" + frequency + '\'' +
                ", route='" + route + '\'' +
                ", refills=" + refills +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}