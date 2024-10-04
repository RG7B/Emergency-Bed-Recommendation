package com.consortium.medical.dto;

import lombok.Data;

/**
 * Requête pour la recommandation de lit.
 */
@Data
public class BedRecommendationRequest {
    private String patientId;
    private double latitude;
    private double longitude;
    private String symptoms;
}