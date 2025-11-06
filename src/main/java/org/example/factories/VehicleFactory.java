package org.example.factories;

import org.example.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Vehicle createVehicle(String regNr, String model, int year) {
        try {
            return new Vehicle(regNr, model, year);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }
}
