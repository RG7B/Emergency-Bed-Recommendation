package com.consortium.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Réponse pour la recommandation de lit.
 */
@Data
@AllArgsConstructor
public class BedRecommendationResponse {
    private String hospitalId;
    private String hospitalName;
}