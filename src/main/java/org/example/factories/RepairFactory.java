package org.example.factories;

import org.example.entities.Booking;
import org.example.entities.Repair;
import org.example.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RepairFactory extends BookingFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Booking createBooking(Vehicle vehicle, LocalDateTime date, String email, String measure) {
        try {
            return new Repair(vehicle, date, email, measure);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }
}
