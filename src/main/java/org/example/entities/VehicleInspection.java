package org.example.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class VehicleInspection extends Booking {

    public VehicleInspection(Vehicle vehicle, LocalDate date, LocalTime time, String email, boolean isReady) {
        super(vehicle, time, date, 500, email, isReady);
    }
}
