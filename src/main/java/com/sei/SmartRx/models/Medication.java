package com.sei.SmartRx.models;

public class Medication {

    private Long id;

    private String name;

    private String genericName;
    private String contraIndication;
    private String sideEffects;
    private String indredients;


    public Medication() {
    }

    public Medication(Long id, String name, String genericName, String contraIndication, String sideEffects, String indredients) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.contraIndication = contraIndication;
        this.sideEffects = sideEffects;
        this.indredients = indredients;
    }
}
