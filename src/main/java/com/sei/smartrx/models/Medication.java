package com.sei.smartrx.models;
import com.sei.smartrx.models.Prescription;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String genericName;
    @Column
    private String contraIndication;
    @Column
    private String sideEffects;
    @Column
    private String ingredients;

    //Many Medications can belong to Many Prescriptions
    @JsonIgnore
    @ManyToMany(mappedBy = "medicationList")
    private List<Prescription> prescriptionList;


    public Medication() {
    }

    public Medication(Long id, String name, String genericName, String contraIndication, String sideEffects, String ingredients) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.contraIndication = contraIndication;
        this.sideEffects = sideEffects;
        this.ingredients = ingredients;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
