package org.example.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Repair extends Booking {

    private String measure;

    public Repair(Vehicle vehicle, LocalDate date, String email, String measure) {
        super(vehicle,date, email);
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Repair{" +
                super.toString() +
                "measure='" + measure + '\'' +
                '}';
    }
}
