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
        hospitalIds.add("1");
        hospitalIds.add("2");
        hospitalIds.add("3");
        hospitalIds.add("4");
        Attribute hospitalIdAttr = new Attribute("hospital_id", hospitalIds);
        attributes.add(hospitalIdAttr);

        // Créer le jeu de données
        Instances dataset = new Instances("HospitalData", attributes, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Générer des données synthétiques
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            double latitude = 48.80 + rand.nextDouble() * 0.1;
            double longitude = 2.30 + rand.nextDouble() * 0.1;
            int severity = rand.nextInt(10) + 1;
            String hospitalId = hospitalIds.get(rand.nextInt(hospitalIds.size()));
            addInstance(dataset, latitude, longitude, severity, hospitalId);
        }

        return dataset;
    }

    /**
     * Ajoute une instance au jeu de données.
     */
    private static void addInstance(Instances dataset, double latitude, double longitude, int severity, String hospitalId) {
        double[] instanceValues = new double[dataset.numAttributes()];
        instanceValues[0] = latitude;
        instanceValues[1] = longitude;
        instanceValues[2] = severity;
        instanceValues[3] = dataset.attribute("hospital_id").indexOfValue(hospitalId);

        dataset.add(new DenseInstance(1.0, instanceValues));
    }
}