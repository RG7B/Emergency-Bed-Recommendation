package com.consortium.medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Modèle représentant un hôpital.
 */
@Data
@AllArgsConstructor
public class Hospital {
    private String id;
    private String name;
    private int availableBeds;
    private double latitude;
    private double longitude;
}