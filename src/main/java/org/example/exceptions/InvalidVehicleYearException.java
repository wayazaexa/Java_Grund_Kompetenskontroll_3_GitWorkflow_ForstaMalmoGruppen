package org.example.exceptions;

public class InvalidVehicleYearException extends RuntimeException {
    public InvalidVehicleYearException(String message) {
        super(message);
    }
}
