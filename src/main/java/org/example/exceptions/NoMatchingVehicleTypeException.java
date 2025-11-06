package org.example.exceptions;

public class NoMatchingVehicleTypeException extends RuntimeException {
    public NoMatchingVehicleTypeException(String message) {
        super(message);
    }
}
