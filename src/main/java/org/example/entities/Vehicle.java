package org.example.entities;

import org.example.exceptions.EmptyModelException;
import org.example.exceptions.InvalidRegNrException;
import org.example.exceptions.InvalidVehicleYearException;

import java.time.LocalDate;

public class Vehicle {

    private String regNr;
    private String model;
    private int year;

    public Vehicle(String regNr, String model, int year) {
        if (regNr == null || regNr.isBlank()) {
            throw new InvalidRegNrException("Registration number is not valid");
        }
        this.regNr = regNr;

        if (model == null || model.isBlank()) {
            throw new EmptyModelException("Vehicle model is required");
        }
        this.model = model;

        if (year < 1950 || year > LocalDate.now().getYear()) {
            throw new InvalidVehicleYearException("Invalid vehicle manufacturing year");
        }
        this.year = year;
    }

    public String getRegNr() {
        return regNr;
    }

    public void setRegNr(String regNr) {
        this.regNr = regNr;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "regNr ='" + regNr + '\'' +
                ", model ='" + model + '\'' +
                ", year =" + year +
                '}';
    }

}
