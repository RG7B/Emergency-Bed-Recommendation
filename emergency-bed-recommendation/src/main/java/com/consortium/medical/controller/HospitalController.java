package com.consortium.medical.controller;

import com.consortium.medical.model.Hospital;
import com.consortium.medical.service.HospitalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des hôpitaux.
 */
@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    /**
     * Constructeur avec injection du service.
     */
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    /**
     * Endpoint pour obtenir tous les hôpitaux.
     */
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    /**
     * Endpoint pour ajouter un hôpital (fonctionnalité future).
     */
    @PostMapping
    public void addHospital(@RequestBody Hospital hospital) {
        hospitalService.addHospital(hospital);
    }
}