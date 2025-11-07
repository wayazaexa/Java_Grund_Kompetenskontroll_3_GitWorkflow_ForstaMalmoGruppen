package org.example.factories;

import org.example.entities.Booking;
import org.example.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Booking createBooking(Vehicle vehicle, LocalDateTime date, String email) {
        try {
            return new Booking(vehicle, date, email);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

//    public Booking createBooking(Vehicle vehicle, LocalDate date, String email, String measure) {
//        return null;
//    }
}