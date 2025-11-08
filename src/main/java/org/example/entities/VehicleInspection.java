package org.example.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VehicleInspection extends Booking {

    public VehicleInspection(Vehicle vehicle, LocalDateTime date, String email) {
        super(vehicle, date, email, BookingType.INSPECTION);
        this.setPrice(500);
        this.setBookingType(BookingType.INSPECTION);
    }

    @Override
    public String toString() {
        return "VehicleInspection - " +
                super.toString();
    }
}
