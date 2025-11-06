package org.example.exceptions;

public class InvalidVehicleException extends RuntimeException {
    public InvalidVehicleException(String message) {
        super(message);
    }
}
