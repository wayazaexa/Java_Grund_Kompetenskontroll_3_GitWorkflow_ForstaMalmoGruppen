package org.example.entities;

public class Vehicle {

    private String regNr;
    private String model;
    private int year;


    public Vehicle(String regNr, String model, int year) {
        this.regNr = regNr;
        this.model = model;
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
