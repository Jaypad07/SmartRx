package com.sei.smartrx.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="prescriptions")
public class Prescription {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @Column
    private String patientName;

    @Column
    private String medicationName;

    @Column
    private Double doseage;

    @Column
    private int quantity;

    @Column
    private String frequency;

    @Column
    private String route;

    @Column
    private int refills;

    @Column
    private LocalDate endDate;

    @Column
    private Boolean status;
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
     * Prescriptions have a Many-to-Many relationship to Medication
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
     * Prescriptions have a Many-To-One relationship with the User
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
