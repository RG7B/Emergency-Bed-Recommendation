package com.consortium.medical.ml;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;

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
        // L'attribut classe (hospital_id) est de type nominal (string)
        attributes.add(new Attribute("hospital_id", (ArrayList<String>) null));

        // Créer le jeu de données
        Instances dataset = new Instances("HospitalData", attributes, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Ajouter des instances (données factices)
        addInstance(dataset, 48.8566, 2.3522, 9, "1");
        addInstance(dataset, 48.8666, 2.3522, 5, "2");
        addInstance(dataset, 48.8566, 2.3622, 3, "3");
        addInstance(dataset, 48.8466, 2.3522, 7, "4");

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
        instanceValues[3] = dataset.attribute("hospital_id").addStringValue(hospitalId);

        dataset.add(new DenseInstance(1.0, instanceValues));
    }
}