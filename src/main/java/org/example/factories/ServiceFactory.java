package org.example.factories;

import org.example.entities.Booking;
import org.example.entities.Service;
import org.example.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class ServiceFactory extends BookingFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Booking createBooking(Vehicle vehicle, LocalDate date, String email) {
        try {
            return new Service(vehicle, date, email);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }
}
