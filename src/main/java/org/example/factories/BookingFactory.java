package org.example.factories;

import org.example.entities.Booking;
import org.example.entities.Vehicle;

import java.time.LocalDate;


public class BookingFactory {
    public Booking createBooking(Vehicle vehicle, LocalDate date, String email) {
            try {
                return new Booking(vehicle, date, email);
            } catch (RuntimeException e) {
                // Log error message
                return null;
            }
        }
    }