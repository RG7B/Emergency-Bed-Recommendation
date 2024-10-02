package com.consortium.medical.service;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.NoAvailableBedException;
import com.consortium.medical.exception.PredictionException;
import com.consortium.medical.ml.PredictionModel;
import com.consortium.medical.model.Hospital;
import com.consortium.medical.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service pour la logique de recommandation de lits.
 */
@Service
public class BedRecommendationService {

    private final HospitalRepository hospitalRepository;
    private final PredictionModel predictionModel;

    /**
     * Constructeur avec injection du repository et initialisation du modèle.
     */
    public BedRecommendationService(HospitalRepository hospitalRepository) throws Exception {
        this.hospitalRepository = hospitalRepository;
        this.predictionModel = new PredictionModel();
    }

    /**
     * Méthode pour trouver un lit disponible.
     */
    public BedRecommendationResponse findAvailableBed(BedRecommendationRequest request) {
        try {
            int symptomSeverity = evaluateSymptomSeverity(request.getSymptoms());

            String predictedHospitalId = predictionModel.predictHospital(
                    request.getLatitude(),
                    request.getLongitude(),
                    symptomSeverity
            );

            Optional<Hospital> hospitalOpt = hospitalRepository.findById(predictedHospitalId);

            Hospital hospital = hospitalOpt.orElseThrow(() ->
                    new NoAvailableBedException("Aucun hôpital disponible"));

            return new BedRecommendationResponse(hospital.getId(), hospital.getName());
        } catch (Exception e) {
            throw new PredictionException("Erreur lors de la prédiction de l'hôpital", e);
        }
    }

    /**
     * Évaluation simplifiée de la gravité des symptômes.
     */
    private int evaluateSymptomSeverity(String symptoms) {
        if (symptoms.toLowerCase().contains("douleurs thoraciques")) {
            return 9;
        } else if (symptoms.toLowerCase().contains("fièvre")) {
            return 5;
        } else {
            return 3;
        }
    }
}