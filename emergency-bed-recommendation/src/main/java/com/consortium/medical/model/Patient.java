package com.consortium.medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Modèle représentant un patient.
 */
@Data
@AllArgsConstructor
public class Patient {
    private String id;
    private String name;
    private int age;
    private String symptoms;
}