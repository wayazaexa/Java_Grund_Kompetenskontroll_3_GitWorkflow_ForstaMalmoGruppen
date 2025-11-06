package org.example.exceptions;

public class EmptyModelException extends RuntimeException {
    public EmptyModelException(String message) {
        super(message);
    }
}
