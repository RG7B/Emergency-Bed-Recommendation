package com.consortium.medical.service;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.PredictionException;
import com.consortium.medical.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitaire pour le service de recommandation de lits.
 */
public class BedRecommendationServiceTest {

    private BedRecommendationService bedService;

    @BeforeEach
    public void setUp() throws Exception {
        HospitalRepository hospitalRepository = new HospitalRepository();
        bedService = new BedRecommendationService(hospitalRepository);
    }

    @Test
    public void testFindAvailableBed() {
        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setLatitude(48.8566);
        request.setLongitude(2.3522);
        request.setSymptoms("Douleurs thoraciques");

        BedRecommendationResponse response = bedService.findAvailableBed(request);

        assertEquals("1", response.getHospitalId());
        assertEquals("Hôpital A", response.getHospitalName());
    }

    @Test
    public void testFindAvailableBed_PredictionException() {
        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setLatitude(0); // Valeur invalide pour provoquer une erreur
        request.setLongitude(0);
        request.setSymptoms("Symptôme inconnu");

        assertThrows(PredictionException.class, () -> {
            bedService.findAvailableBed(request);
        });
    }
}