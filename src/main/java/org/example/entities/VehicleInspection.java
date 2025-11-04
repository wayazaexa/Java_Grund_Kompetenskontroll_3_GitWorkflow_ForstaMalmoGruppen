package org.example.entities;

public class VehicleInspection extends Booking {

    public VehicleInspection(Vehicle vehicle, int date, String email, boolean isReady) {
        super(vehicle, date, 500, email, isReady);
    }
}
