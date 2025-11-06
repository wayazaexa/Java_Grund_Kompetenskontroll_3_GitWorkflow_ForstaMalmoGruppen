package org.example.factories;
import org.example.entities.Vehicle;

public class VehicleFactory {
        public Vehicle createVehicle(String regNr, String model, int year) {
            try {
                return new Vehicle(regNr, model, year);
            } catch (RuntimeException e) {
                // Log error message
                return null;
            }
        }
    }
