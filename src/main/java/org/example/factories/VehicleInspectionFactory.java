package org.example.factories;

import org.example.entities.Vehicle;
import org.example.entities.VehicleInspection;

import java.time.LocalDate;

public class VehicleInspectionFactory {
    public VehicleInspection createInspection(Vehicle vehicle, LocalDate date, String email) {
        try {
            return new VehicleInspection(vehicle, date, email);
        } catch (RuntimeException e) {
            // Log error message
            return null;
        }
    }
}
