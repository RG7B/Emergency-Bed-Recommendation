package com.consortium.medical.ml;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Random;

/**
 * Préparation des données pour le modèle de machine learning.
 */
public class DataPreparation {

    /**
     * Crée et retourne le jeu de données.
     */
    public static Instances getDataset() {
        // Définir les attributs
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("latitude"));
        attributes.add(new Attribute("longitude"));
        attributes.add(new Attribute("symptom_severity"));

        // L'attribut classe (hospital_id) est de type nominal
        ArrayList<String> hospitalIds = new ArrayList<>();
        hospitalIds.add("1"); // Hôpital A
        hospitalIds.add("2"); // Hôpital B
        hospitalIds.add("3"); // Hôpital C
        hospitalIds.add("4"); // Hôpital D
        Attribute hospitalIdAttr = new Attribute("hospital_id", hospitalIds);
        attributes.add(hospitalIdAttr);

        // Créer le jeu de données
        Instances dataset = new Instances("HospitalData", attributes, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Générer des données cohérentes pour chaque hôpital
        generateDataForHospital(dataset, "1", 48.80, 48.85, 2.30, 2.35, 7, 10); // Hôpital A
        generateDataForHospital(dataset, "2", 48.85, 48.90, 2.35, 2.40, 4, 6);  // Hôpital B
        generateDataForHospital(dataset, "3", 48.80, 48.85, 2.35, 2.40, 1, 3);  // Hôpital C
        generateDataForHospital(dataset, "4", 48.85, 48.90, 2.30, 2.35, 1, 10); // Hôpital D

        return dataset;
    }

    /**
     * Génère des données pour un hôpital spécifique.
     *
     * @param dataset       Le jeu de données
     * @param hospitalId    L'identifiant de l'hôpital
     * @param latMin        Latitude minimale
     * @param latMax        Latitude maximale
     * @param lonMin        Longitude minimale
     * @param lonMax        Longitude maximale
     * @param severityMin   Gravité minimale des symptômes
     * @param severityMax   Gravité maximale des symptômes
     */
    private static void generateDataForHospital(Instances dataset, String hospitalId,
                                                double latMin, double latMax,
                                                double lonMin, double lonMax,
                                                int severityMin, int severityMax) {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) { // Générer 50 instances par hôpital
            double latitude = latMin + rand.nextDouble() * (latMax - latMin);
            double longitude = lonMin + rand.nextDouble() * (lonMax - lonMin);
            int severity = rand.nextInt(severityMax - severityMin + 1) + severityMin;
            addInstance(dataset, latitude, longitude, severity, hospitalId);
        }
    }

    /**
     * Ajoute une instance au jeu de données.
     *
     * @param dataset      Le jeu de données
     * @param latitude     La latitude
     * @param longitude    La longitude
     * @param severity     La gravité des symptômes
     * @param hospitalId   L'identifiant de l'hôpital
     */
    private static void addInstance(Instances dataset, double latitude, double longitude,
                                    int severity, String hospitalId) {
        double[] instanceValues = new double[dataset.numAttributes()];
        instanceValues[0] = latitude;
        instanceValues[1] = longitude;
        instanceValues[2] = severity;
        instanceValues[3] = dataset.attribute("hospital_id").indexOfValue(hospitalId);

        dataset.add(new DenseInstance(1.0, instanceValues));
    }
}
