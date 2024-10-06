package com.consortium.medical.ml;

import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Modèle de prédiction utilisant Weka.
 */
@Component
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
        classifier = new J48(); // Arbre de décision

        // Entraîner le modèle
        classifier.buildClassifier(dataset);
    }

    /**
     * Prédit l'hôpital le plus approprié.
     *
     * @param latitude         La latitude du patient
     * @param longitude        La longitude du patient
     * @param symptomSeverity  La gravité des symptômes
     * @return L'identifiant de l'hôpital prédit
     * @throws Exception En cas d'erreur lors de la prédiction
     */
    public String predictHospital(double latitude, double longitude, int symptomSeverity) throws Exception {
        // Valider les entrées
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180 || symptomSeverity < 1 || symptomSeverity > 10) {
            throw new IllegalArgumentException("Valeurs d'entrée invalides.");
        }

        // Créer une nouvelle instance à prédire
        Instance instance = new DenseInstance(dataset.numAttributes());
        instance.setDataset(dataset);
        instance.setValue(dataset.attribute("latitude"), latitude);
        instance.setValue(dataset.attribute("longitude"), longitude);
        instance.setValue(dataset.attribute("symptom_severity"), symptomSeverity);

        // Faire la prédiction
        double result = classifier.classifyInstance(instance);

        // Vérifier si le résultat est valide
        if (Double.isNaN(result)) {
            throw new Exception("Le modèle n'a pas pu faire de prédiction valide.");
        }

        // Obtenir la valeur prédite pour l'attribut classe (hospital_id)
        String predictedHospitalId = dataset.classAttribute().value((int) result);

        return predictedHospitalId;
    }
}
