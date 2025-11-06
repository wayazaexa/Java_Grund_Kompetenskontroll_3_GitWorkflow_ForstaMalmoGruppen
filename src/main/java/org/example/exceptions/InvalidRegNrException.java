package org.example.exceptions;

public class InvalidRegNrException extends RuntimeException {
    public InvalidRegNrException(String message) {
        super(message);
    }
}
