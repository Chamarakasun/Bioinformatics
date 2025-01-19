/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

/**
 *
 * @author User
 */
import java.util.HashMap;
import java.util.Scanner;

public class ChouFasmanPredictorOption3 {
    
    // Define propensity tables for α-helices and β-sheets
    private static final HashMap<Character, Double> alphaPropensities = new HashMap<>();
    private static final HashMap<Character, Double> betaPropensities = new HashMap<>();
    
    static {
        // Initialize propensity values (example values, you may need to update them)
        alphaPropensities.put('A', 1.42);
        alphaPropensities.put('C', 0.63);
        alphaPropensities.put('D', 0.32);
        alphaPropensities.put('E', 0.90);
        alphaPropensities.put('F', 1.03);
        alphaPropensities.put('G', 0.57);
        alphaPropensities.put('H', 0.83);
        alphaPropensities.put('I', 1.36);
        alphaPropensities.put('K', 0.64);
        alphaPropensities.put('L', 1.03);
        alphaPropensities.put('M', 1.20);
        alphaPropensities.put('N', 0.66);
        alphaPropensities.put('P', 0.57);
        alphaPropensities.put('Q', 0.76);
        alphaPropensities.put('R', 0.67);
        alphaPropensities.put('S', 0.67);
        alphaPropensities.put('T', 0.67);
        alphaPropensities.put('V', 1.08);
        alphaPropensities.put('W', 0.82);
        alphaPropensities.put('Y', 0.83);

        betaPropensities.put('A', 0.73);
        betaPropensities.put('C', 0.46);
        betaPropensities.put('D', 1.03);
        betaPropensities.put('E', 1.13);
        betaPropensities.put('F', 0.63);
        betaPropensities.put('G', 1.00);
        betaPropensities.put('H', 0.72);
        betaPropensities.put('I', 0.49);
        betaPropensities.put('K', 0.70);
        betaPropensities.put('L', 0.58);
        betaPropensities.put('M', 0.77);
        betaPropensities.put('N', 0.83);
        betaPropensities.put('P', 0.35);
        betaPropensities.put('Q', 0.78);
        betaPropensities.put('R', 0.75);
        betaPropensities.put('S', 0.54);
        betaPropensities.put('T', 0.67);
        betaPropensities.put('V', 0.50);
        betaPropensities.put('W', 0.55);
        betaPropensities.put('Y', 0.63);
    }

    private static final double HELIX_THRESHOLD = 1.00; // Threshold for α-helix
    private static final double SHEET_THRESHOLD = 0.80; // Threshold for β-sheet

    public static void main(String[] args) {
        // Accept amino acid sequence from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amino acid sequence: ");
        String aminoAcidSequence = scanner.nextLine().toUpperCase();
        
        // Validate the sequence
        if (!isValidSequence(aminoAcidSequence)) {
            System.out.println("Invalid amino acid sequence. Please enter only valid amino acids (A, C, D, E, F, G, H, I, K, L, M, N, P, Q, R, S, T, V, W, Y).");
            return;
        }

        // Predict secondary structure
        String predictedStructure = predictSecondaryStructure(aminoAcidSequence);
        
        // Output the predicted secondary structure
        System.out.println("Predicted Secondary Structure: " + predictedStructure);
        scanner.close();
    }

    // Validate the amino acid sequence
    private static boolean isValidSequence(String sequence) {
        for (char aminoAcid : sequence.toCharArray()) {
            if (!alphaPropensities.containsKey(aminoAcid)) {
                return false; // Invalid amino acid found
            }
        }
        return true; // All amino acids are valid
    }

    // Predict secondary structure using the Chou-Fasman method
    private static String predictSecondaryStructure(String sequence) {
        StringBuilder predictedStructure = new StringBuilder();
        int length = sequence.length();
        
        // Loop through the amino acid sequence
        for (int i = 0; i < length; i++) {
            // Get the current amino acid
            char aminoAcid = sequence.charAt(i);
            
            // Retrieve propensity values
            double alphaPropensity = alphaPropensities.get(aminoAcid);
            double betaPropensity = betaPropensities.get(aminoAcid);

            // Apply thresholds to predict structures
            if (alphaPropensity > HELIX_THRESHOLD) {
                predictedStructure.append('H'); // Mark as α-helix
            } else if (betaPropensity > SHEET_THRESHOLD) {
                predictedStructure.append('E'); // Mark as β-sheet
            } else {
                predictedStructure.append('-'); // Mark as coil (no defined structure)
            }
        }
        
        return predictedStructure.toString();
    }
}

