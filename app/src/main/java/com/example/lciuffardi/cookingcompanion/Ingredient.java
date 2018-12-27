package com.example.lciuffardi.cookingcompanion;

/**
 * Created by Luigi Ciuffardi on 9/30/2017.
 */

public class Ingredient {
    private long id;
    private String name;
    private String quantityNumber;
    private String quantityUnitOfMeasurement;
    private String expiration;
    private String specialNotes;

    public Ingredient() {

    }

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

    public void setQuantityNumber(String quantityNumber) {
        this.quantityNumber = quantityNumber;
    }

    public String getQuantityUnitOfMeasurement() {
        return quantityUnitOfMeasurement;
    }

    public void setQuantityUnitOfMeasurement(String quantityUnitOfMeasurement) {
        this.quantityUnitOfMeasurement = quantityUnitOfMeasurement;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }
}
