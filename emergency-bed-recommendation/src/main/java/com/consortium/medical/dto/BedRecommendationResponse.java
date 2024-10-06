package com.consortium.medical.dto;

/**
 * Réponse pour la recommandation de lit.
 */
public class BedRecommendationResponse {

    private String hospitalId;
    private String hospitalName;

    /**
     * Constructeur avec tous les champs.
     *
     * @param hospitalId   L'ID de l'hôpital recommandé
     * @param hospitalName Le nom de l'hôpital recommandé
     */
    public BedRecommendationResponse(String hospitalId, String hospitalName) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
    }

    // Getter et setter pour hospitalId
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    // Getter et setter pour hospitalName
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
