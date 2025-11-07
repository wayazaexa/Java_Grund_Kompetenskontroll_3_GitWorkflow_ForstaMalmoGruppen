package org.example.entities;

import java.time.LocalDate;

public class VehicleInspection extends Booking {

    public VehicleInspection(Vehicle vehicle, LocalDate date, String email) {
        super(vehicle, date, email);
        this.setPrice(500);
    }

    @Override
    public String toString() {
        return "VehicleInspection - " +
                super.toString();
    }
}
