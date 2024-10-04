package com.consortium.medical.service;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.NoAvailableBedException;
import com.consortium.medical.exception.PredictionException;
import com.consortium.medical.ml.PredictionModel;
import com.consortium.medical.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BedRecommendationServiceTest {

    private BedRecommendationService bedService;
    private HospitalRepository hospitalRepository;

    @BeforeEach
    public void setUp() throws Exception {
        hospitalRepository = new HospitalRepository();
        PredictionModel predictionModel = new PredictionModel();
        bedService = new BedRecommendationService(hospitalRepository, predictionModel);
    }

    @Test
    public void testFindAvailableBed_Success() {
        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setPatientId("12345");
        request.setLatitude(48.8566);
        request.setLongitude(2.3522);
        request.setSymptoms("Douleurs thoraciques");

        BedRecommendationResponse response = bedService.findAvailableBed(request);

        assertNotNull(response);
        assertNotNull(response.getHospitalId());
        assertNotNull(response.getHospitalName());
    }

    @Test
    public void testFindAvailableBed_NoAvailableBed() {
        // Supprimer tous les hôpitaux pour simuler qu'aucun hôpital n'est disponible
        hospitalRepository.findAll().clear();

        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setPatientId("12345");
        request.setLatitude(48.8566);
        request.setLongitude(2.3522);
        request.setSymptoms("Douleurs thoraciques");

        assertThrows(NoAvailableBedException.class, () -> {
            bedService.findAvailableBed(request);
        });
    }

    @Test
    public void testFindAvailableBed_PredictionException() throws Exception {
        // Créer un mock du PredictionModel qui lance une exception
        PredictionModel predictionModelMock = mock(PredictionModel.class);
        when(predictionModelMock.predictHospital(anyDouble(), anyDouble(), anyInt()))
                .thenThrow(new Exception("Erreur de prédiction"));

        // Injecter le mock dans le service
        bedService = new BedRecommendationService(hospitalRepository, predictionModelMock);

        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setPatientId("12345");
        request.setLatitude(48.8566);
        request.setLongitude(2.3522);
        request.setSymptoms("Douleurs thoraciques");

        assertThrows(PredictionException.class, () -> {
            bedService.findAvailableBed(request);
        });
    }
}
