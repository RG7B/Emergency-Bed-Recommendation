package com.consortium.medical.exception;

/**
 * Exception pour les entrées invalides.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}