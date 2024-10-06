package com.consortium.medical.service;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.NoAvailableBedException;
import com.consortium.medical.exception.PredictionException;
import com.consortium.medical.ml.PredictionModel;
import com.consortium.medical.model.Hospital;
import com.consortium.medical.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BedRecommendationServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private PredictionModel predictionModel;

    @InjectMocks
    private BedRecommendationService bedService;

    private BedRecommendationRequest request;

    @BeforeEach
    public void setUp() throws Exception {
        request = new BedRecommendationRequest();
        request.setPatientId("12345");
        request.setLatitude(48.85);
        request.setLongitude(2.35);
        request.setSymptoms("Douleurs thoraciques");
    }

    @Test
    public void testFindAvailableBed_Success() throws Exception {
        when(predictionModel.predictHospital(anyDouble(), anyDouble(), anyInt())).thenReturn("1");
        Hospital hospital = new Hospital(
            "1",
            "Hôpital A",
            "Adresse A",
            10,
            48.8566,
            2.3522
        );
        when(hospitalRepository.findById("1")).thenReturn(Optional.of(hospital));

        BedRecommendationResponse response = bedService.findAvailableBed(request);

        assertNotNull(response);
        assertEquals("1", response.getHospitalId());
        assertEquals("Hôpital A", response.getHospitalName());

        verify(predictionModel).predictHospital(anyDouble(), anyDouble(), anyInt());
        verify(hospitalRepository).findById("1");
    }

    @Test
    public void testFindAvailableBed_NoAvailableHospital() throws Exception {
        when(predictionModel.predictHospital(anyDouble(), anyDouble(), anyInt())).thenReturn("1");
        when(hospitalRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(NoAvailableBedException.class, () -> {
            bedService.findAvailableBed(request);
        });

        verify(predictionModel).predictHospital(anyDouble(), anyDouble(), anyInt());
        verify(hospitalRepository).findById("1");
    }

    @Test
    public void testFindAvailableBed_PredictionException() throws Exception {
        when(predictionModel.predictHospital(anyDouble(), anyDouble(), anyInt()))
                .thenThrow(new Exception("Erreur de prédiction"));

        assertThrows(PredictionException.class, () -> {
            bedService.findAvailableBed(request);
        });

        verify(predictionModel).predictHospital(anyDouble(), anyDouble(), anyInt());
        verify(hospitalRepository, never()).findById(anyString());
    }

    @Test
    public void testEvaluateSymptomSeverity() {
        int severity = bedService.evaluateSymptomSeverity("Douleurs thoraciques");
        assertEquals(9, severity);

        severity = bedService.evaluateSymptomSeverity("Fièvre");
        assertEquals(5, severity);

        severity = bedService.evaluateSymptomSeverity("Éruption cutanée");
        assertEquals(2, severity);

        severity = bedService.evaluateSymptomSeverity("Symptôme inconnu");
        assertEquals(3, severity);
    }
}
