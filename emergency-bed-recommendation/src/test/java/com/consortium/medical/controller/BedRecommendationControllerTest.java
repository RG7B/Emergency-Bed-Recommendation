package com.consortium.medical.controller;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.exception.InvalidInputException;
import com.consortium.medical.service.BedRecommendationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test unitaire pour le contrôleur de recommandation de lits.
 */
public class BedRecommendationControllerTest {

    @Test
    public void testRecommendBed_InvalidInputException() {
        BedRecommendationService bedService = mock(BedRecommendationService.class);
        BedRecommendationController controller = new BedRecommendationController(bedService);

        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setLatitude(0); // Valeur invalide
        request.setLongitude(0);
        request.setSymptoms("");

        assertThrows(InvalidInputException.class, () -> {
            controller.recommendBed(request);
        });
    }
}