package com.consortium.medical.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestionnaire global des exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestion des exceptions de type InvalidInputException.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Gestion des exceptions de type NoAvailableBedException.
     */
    @ExceptionHandler(NoAvailableBedException.class)
    public ResponseEntity<String> handleNoAvailableBedException(NoAvailableBedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Gestion des exceptions de type PredictionException.
     */
    @ExceptionHandler(PredictionException.class)
    public ResponseEntity<String> handlePredictionException(PredictionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * Gestion des autres exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne est survenue.");
    }
}