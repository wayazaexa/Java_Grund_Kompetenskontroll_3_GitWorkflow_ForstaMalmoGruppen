package org.example.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Repair extends Booking {

    private String measure;

    public Repair(Vehicle vehicle, LocalTime time, LocalDate date, int price, String email, boolean isReady, String measure) {
        super(vehicle, time, date, price, email, isReady);
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
                "measure='" + measure + '\'' +
                '}';
    }
}
