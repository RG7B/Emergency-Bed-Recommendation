package com.consortium.medical.service;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.NoAvailableBedException;
import com.consortium.medical.exception.PredictionException;
import com.consortium.medical.ml.PredictionModel;
import com.consortium.medical.model.Hospital;
import com.consortium.medical.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Constructeur avec injection des dépendances.
     */
    @Autowired
    public BedRecommendationService(HospitalRepository hospitalRepository, PredictionModel predictionModel) {
        this.hospitalRepository = hospitalRepository;
        this.predictionModel = predictionModel;
    }

    /**
     * Méthode pour trouver un lit disponible.
     *
     * @param request La requête de recommandation de lit
     * @return La réponse contenant l'hôpital recommandé
     */
    public BedRecommendationResponse findAvailableBed(BedRecommendationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La requête ne peut pas être nulle");
        }

        int symptomSeverity = evaluateSymptomSeverity(request.getSymptoms());

        String predictedHospitalId;
        try {
            predictedHospitalId = predictionModel.predictHospital(
                    request.getLatitude(),
                    request.getLongitude(),
                    symptomSeverity
            );
        } catch (Exception e) {
            throw new PredictionException("Erreur lors de la prédiction de l'hôpital", e);
        }

        Optional<Hospital> hospitalOpt = hospitalRepository.findById(predictedHospitalId);

        Hospital hospital = hospitalOpt.orElseThrow(() ->
                new NoAvailableBedException("Aucun hôpital disponible"));

        return new BedRecommendationResponse(hospital.getId(), hospital.getName());
    }

    /**
     * Évaluation simplifiée de la gravité des symptômes.
     *
     * @param symptoms Les symptômes du patient
     * @return Un entier représentant la gravité des symptômes
     */
    int evaluateSymptomSeverity(String symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
            throw new IllegalArgumentException("Les symptômes ne peuvent pas être vides");
        }

        String symptomsLower = symptoms.toLowerCase();
        if (symptomsLower.contains("douleurs thoraciques")) {
            return 9;
        } else if (symptomsLower.contains("fièvre")) {
            return 5;
        } else if (symptomsLower.contains("éruption") || symptomsLower.contains("rash")) {
            return 2;
        } else {
            return 3; // Gravité par défaut
        }
    }
}

