package com.consortium.medical.controller;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.InvalidInputException;
import com.consortium.medical.service.BedRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour les recommandations de lits.
 */
@RestController
@RequestMapping("/api/v1")
public class BedRecommendationController {

    private final BedRecommendationService bedService;

    /**
     * Constructeur avec injection du service.
     */
    public BedRecommendationController(BedRecommendationService bedService) {
        this.bedService = bedService;
    }

    /**
     * Endpoint pour obtenir une recommandation de lit.
     */
    @PostMapping("/recommend-bed")
    public ResponseEntity<BedRecommendationResponse> recommendBed(@RequestBody BedRecommendationRequest request) {
        validateRequest(request);
        BedRecommendationResponse response = bedService.findAvailableBed(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Validation de la requête entrante.
     */
    private void validateRequest(BedRecommendationRequest request) {
        if (request.getPatientId() == null || request.getPatientId().isEmpty()) {
            throw new InvalidInputException("Le patientId est requis");
        }
        if (request.getLatitude() == 0 || request.getLongitude() == 0) {
            throw new InvalidInputException("Les coordonnées géographiques sont requises");
        }
        if (request.getSymptoms() == null || request.getSymptoms().isEmpty()) {
            throw new InvalidInputException("Les symptômes sont requis");
        }
    }
}