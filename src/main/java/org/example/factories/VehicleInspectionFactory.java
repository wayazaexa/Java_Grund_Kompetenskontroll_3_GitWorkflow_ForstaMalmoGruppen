package org.example.factories;

import org.example.entities.Booking;
import org.example.entities.BookingType;
import org.example.entities.Vehicle;
import org.example.entities.VehicleInspection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VehicleInspectionFactory extends BookingFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Booking createBooking(Vehicle vehicle, LocalDateTime date, String email,BookingType bookingType) {
        try {
            return new VehicleInspection(vehicle, date, email);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }
}