package org.example.factories;

import org.example.entities.Repair;
import org.example.entities.Vehicle;

import java.time.LocalDate;

public class RepairFactory {
    public Repair createRepair(Vehicle vehicle, LocalDate date, String email, String measure) {
        try {
            return new Repair(vehicle, date, email, measure);
        } catch (RuntimeException e) {
            // Log error message
            return null;
        }
    }
}

