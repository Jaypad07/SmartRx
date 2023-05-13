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
    private int refills;

    @Column
    private LocalDate endDate;

    @Column
    private Boolean status;
    /**
     * Create both an empty default constructor and a loaded constructor
     */
    public Prescription() {}

    public Prescription(String patientName, int refills, LocalDate endDate, Boolean status) {
        this.patientName = patientName;
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

    public Prescription(String johnBeck, String atorvastatin, double v, int i, String onceAtNight, String byMouth, int i1, int i2, boolean b) {
    }

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }
    /**
     * Prescriptions have a Many-To-One relationship with the User, joined by column user_id on prescriptions.
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
                ", refills=" + refills +
                ", endDate=" + endDate +
                ", status=" + status +
                ", medicationList=" + medicationList +
                ", user=" + user +
                '}';
    }
}
