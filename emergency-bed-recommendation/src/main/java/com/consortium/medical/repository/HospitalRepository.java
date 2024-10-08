package com.consortium.medical.repository; 

import com.consortium.medical.model.Hospital;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dépôt simulé pour les hôpitaux.
 */
@Repository
public class HospitalRepository {

    private final List<Hospital> hospitals = new ArrayList<>();

    /**
     * Constructeur initialisant les données simulées.
     */
    public HospitalRepository() {
        hospitals.add(new Hospital("1", "Hôpital A", "Adresse A", 5, 48.8566, 2.3522));
        hospitals.add(new Hospital("2", "Hôpital B", "Adresse B", 0, 48.8666, 2.3522));
        hospitals.add(new Hospital("3", "Hôpital C", "Adresse C", 2, 48.8566, 2.3622));
        hospitals.add(new Hospital("4", "Hôpital D", "Adresse D", 10, 48.8466, 2.3522));
    }

    /**
     * Récupérer tous les hôpitaux.
     */
    public List<Hospital> findAll() {
        return hospitals;
    }

    /**
     * Trouver un hôpital par son ID.
     */
    public Optional<Hospital> findById(String id) {
        return hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst();
    }
}
