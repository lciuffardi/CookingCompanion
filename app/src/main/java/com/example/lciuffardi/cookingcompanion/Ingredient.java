package com.example.lciuffardi.cookingcompanion;

/**
 * Created by Luigi Ciuffardi on 9/30/2017.
 * Last updated by Luigi Ciuffardi on 12/27/2018.
 */

public class Ingredient {
    private long id;
    private String name;
    private String quantityNumber;
    private String quantityUnitOfMeasurement;
    private String expiration;
    private String specialNotes;

    public Ingredient(String name, String quantityNumber, String quantityUnitOfMeasurement
            , String expiration, String specialNotes) {
        this.name = name;
        this.quantityNumber = quantityNumber;
        this.quantityUnitOfMeasurement = quantityUnitOfMeasurement;
        this.expiration = expiration;
        this.specialNotes = specialNotes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityNumber() {
        return quantityNumber;
    }

    public String getQuantityUnitOfMeasurement() {
        return quantityUnitOfMeasurement;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

}
