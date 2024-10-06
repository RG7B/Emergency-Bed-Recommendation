package com.consortium.medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modèle représentant un hôpital.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    private String id;
    private String name;
    private String address;
    private int availableBeds;
    private double latitude;
    private double longitude;
}
