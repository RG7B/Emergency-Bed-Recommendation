package com.consortium.medical.ml;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Modèle de prédiction utilisant Weka.
 */
public class PredictionModel {

    private Classifier classifier;
    private Instances dataset;

    /**
     * Constructeur initialisant le modèle.
     */
    public PredictionModel() throws Exception {
        // Préparer le jeu de données
        dataset = DataPreparation.getDataset();

        // Initialiser le classifieur
        classifier = new MultilayerPerceptron(); // Réseau de neurones

        // Entraîner le modèle
        classifier.buildClassifier(dataset);
    }

    /**
     * Prédit l'hôpital le plus approprié.
     */
    public String predictHospital(double latitude, double longitude, int symptomSeverity) throws Exception {
        // Créer une nouvelle instance à prédire en utilisant DenseInstance
        Instance instance = new DenseInstance(dataset.numAttributes());

        // Associer cette instance au dataset
        instance.setDataset(dataset);

        // Définir les valeurs pour chaque attribut en utilisant l'attribut du dataset
        instance.setValue(dataset.attribute("latitude"), latitude);
        instance.setValue(dataset.attribute("longitude"), longitude);
        instance.setValue(dataset.attribute("symptom_severity"), symptomSeverity);

        // Faire la prédiction
        double result = classifier.classifyInstance(instance);

        // Vérifier si le résultat est valide
        if (Double.isNaN(result)) {
            throw new Exception("Le modèle n'a pas pu faire de prédiction valide.");
        }

        // Obtenir la valeur prédite pour l'attribut class (hospital_id)
        String predictedHospitalId = dataset.classAttribute().value((int) result);

        return predictedHospitalId;
    }
}
