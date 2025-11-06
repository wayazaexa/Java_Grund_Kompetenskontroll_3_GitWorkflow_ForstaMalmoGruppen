package org.example.factories;

import org.example.entities.Vehicle;
import org.example.entities.VehicleInspection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class VehicleInspectionFactory extends BookingFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public VehicleInspection createInspection(Vehicle vehicle, LocalDate date, String email) {
        try {
            return new VehicleInspection(vehicle, date, email);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }
}
