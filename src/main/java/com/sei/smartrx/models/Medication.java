package com.sei.SmartRx.models;
import com.sei.SmartRx.models.Prescription;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ManyToMany;
import java.util.List;

public class Medication {

    private Long id;

    private String name;

    private String genericName;
    private String contraIndication;
    private String sideEffects;
    private String ingredients;

    //Many Medications can belong to Many Prescriptions
    @JsonIgnore
    @ManyToMany(mappedBy = "medicationList")
    private List<Prescription> prescriptionList;


    public Medication() {
    }

    public Medication(Long id, String name, String genericName, String contraIndication, String sideEffects, String indredients) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.contraIndication = contraIndication;
        this.sideEffects = sideEffects;
        this.ingredients = indredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getContraIndication() {
        return contraIndication;
    }

    public void setContraIndication(String contraIndication) {
        this.contraIndication = contraIndication;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getIndredients() {
        return ingredients;
    }

    public void setIndredients(String indredients) {
        this.ingredients = indredients;
    }
}
