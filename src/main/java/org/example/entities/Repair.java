package org.example.entities;

import java.time.LocalDate;

public class Repair extends Booking {

    private final String measure;

    public Repair(Vehicle vehicle, LocalDate date, String email, String measure) {
        super(vehicle,date, email);
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Repair - " +
                "id: " + getId() +
                ", vehicle: [ " + getVehicle() + " ]" +
                ", date: " + getDate() +
                (getPrice() != 0 ? ", price: " + getPrice() : "") +
                ", email: '" + getEmail() + '\'' +
                ", status: " + getStatus() +
                ", measure: '" + measure + '\'';
    }
}
