package com.consortium.medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Modèle représentant un lit d'hôpital.
 */
@Data
@AllArgsConstructor
public class Bed {
    private String id;
    private String hospitalId;
    private boolean isAvailable;
}