package org.example.factories;


import org.example.entities.Service;
import org.example.entities.Vehicle;

import java.time.LocalDate;

public class ServiceFactory extends BookingFactory {
    public Service createService(Vehicle vehicle, LocalDate date, String email) {
        try {
            return new Service(vehicle, date, email);
        } catch (RuntimeException e) {
            // Log error message
            return null;
        }
    }
}
