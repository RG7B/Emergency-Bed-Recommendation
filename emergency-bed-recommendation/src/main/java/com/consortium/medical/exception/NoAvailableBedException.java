package com.consortium.medical.exception;

/**
 * Exception lorsque aucun lit n'est disponible.
 */
public class NoAvailableBedException extends RuntimeException {
    public NoAvailableBedException(String message) {
        super(message);
    }
}