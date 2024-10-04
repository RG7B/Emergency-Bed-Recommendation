package com.consortium.medical.exception;

/**
 * Exception pour les erreurs lors de la prédiction.
 */
public class PredictionException extends RuntimeException {
    public PredictionException(String message, Throwable cause) {
        super(message, cause);
    }
}