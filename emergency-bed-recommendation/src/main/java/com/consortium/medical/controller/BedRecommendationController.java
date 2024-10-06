package com.consortium.medical.controller;

import com.consortium.medical.dto.BedRecommendationRequest;
import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.InvalidInputException;
import com.consortium.medical.exception.NoAvailableBedException;
import com.consortium.medical.exception.PredictionException;
import com.consortium.medical.service.BedRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour les recommandations de lits.
 */
@Controller
public class BedRecommendationController {

    private final BedRecommendationService bedService;

    /**
     * Constructeur avec injection du service.
     */
    @Autowired
    public BedRecommendationController(BedRecommendationService bedService) {
        this.bedService = bedService;
    }

    /**
     * Affiche la page d'accueil avec le formulaire.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Traite le formulaire de recommandation de lit.
     */
    @PostMapping("/recommend-bed")
    public String recommendBed(
            @RequestParam String patientId,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam String symptoms,
            Model model) {

        BedRecommendationRequest request = new BedRecommendationRequest();
        request.setPatientId(patientId);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setSymptoms(symptoms);

        try {
            // Valider la requête
            validateRequest(request);

            BedRecommendationResponse response = bedService.findAvailableBed(request);
            model.addAttribute("hospitalId", response.getHospitalId());
            model.addAttribute("hospitalName", response.getHospitalName());
            return "result";
        } catch (InvalidInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        } catch (NoAvailableBedException | PredictionException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    /**
     * Endpoint REST pour obtenir une recommandation de lit.
     */
    @PostMapping("/api/v1/recommend-bed")
    @ResponseBody
    public ResponseEntity<BedRecommendationResponse> recommendBedApi(@RequestBody BedRecommendationRequest request) {
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
