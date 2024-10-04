package com.consortium.medical.controller;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.InvalidInputException;
import com.consortium.medical.service.BedRecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BedRecommendationControllerTest {

    @Test
    public void testRecommendBed_Success() {
        BedRecommendationService bedService = mock(BedRecommendationService.class);
        BedRecommendationController controller = new BedRecommendationController(bedService);

        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setPatientId("12345");
        request.setLatitude(48.8566);
        request.setLongitude(2.3522);
        request.setSymptoms("Douleurs thoraciques");

        BedRecommendationResponse expectedResponse = new BedRecommendationResponse("1", "Hôpital A");
        when(bedService.findAvailableBed(request)).thenReturn(expectedResponse);

        ResponseEntity<BedRecommendationResponse> response = controller.recommendBedApi(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testRecommendBed_InvalidInputException() {
        BedRecommendationService bedService = mock(BedRecommendationService.class);
        BedRecommendationController controller = new BedRecommendationController(bedService);

        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setLatitude(0); // Valeur invalide
        request.setLongitude(0);
        request.setSymptoms("");

        assertThrows(InvalidInputException.class, () -> {
            controller.recommendBedApi(request);
        });
    }
}
