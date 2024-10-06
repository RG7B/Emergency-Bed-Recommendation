package com.consortium.medical.ml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PredictionModelTest {

    private PredictionModel predictionModel;

    @BeforeEach
    public void setUp() throws Exception {
        predictionModel = new PredictionModel();
    }

    @Test
    public void testPredictHospital_ValidData() throws Exception {
        String hospitalId = predictionModel.predictHospital(48.82, 2.32, 9);
        assertNotNull(hospitalId);
        assertEquals("1", hospitalId);

        hospitalId = predictionModel.predictHospital(48.88, 2.38, 5);
        assertNotNull(hospitalId);
        assertEquals("2", hospitalId);

        hospitalId = predictionModel.predictHospital(48.83, 2.37, 2);
        assertNotNull(hospitalId);
        assertEquals("3", hospitalId);
    }

    @Test
    public void testPredictHospital_InvalidData() {
        // Attendre une exception pour des données invalides
        assertThrows(IllegalArgumentException.class, () -> {
            predictionModel.predictHospital(100, 200, 0); // Valeurs invalides
        });
    }
}