package com.consortium.medical.service;

import com.consortium.medical.model.Hospital;
import com.consortium.medical.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des hôpitaux.
 */
@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    /**
     * Constructeur avec injection du repository.
     */
    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * Récupérer tous les hôpitaux.
     */
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    /**
     * Ajouter un hôpital (fonctionnalité future).
     */
    public void addHospital(Hospital hospital) {
        // Implémentation future
    }
}