/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformatics;

/**
 *
 * @author Chamara
 */
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class ChouFasmanPredictorOption1 {
    // Propensity tables for each amino acid
    static HashMap<Character, double[]> propensityTable = new HashMap<>();
    static final double helixThreshold = 1.00; // Define a threshold for helices
    static final double sheetThreshold = 1.00; // Define a threshold for sheets

    public static void main(String[] args) {
        // Initialize the propensity table
        initializePropensityTable();

        // Read amino acid sequence from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amino acid sequence: ");
        String aminoAcidSequence = scanner.nextLine().toUpperCase(); // Convert to uppercase

        // Validate the input sequence
        if (!isValidSequence(aminoAcidSequence)) {
            System.out.println("Invalid amino acid sequence. Please use valid characters (A, C, D, E, F, G, H, I, K, L, M, N, P, Q, R, S, T, V, W, Y).");
            return; // Exit the program
        }

        // Predict secondary structure
        ArrayList<String> helices = new ArrayList<>();
        ArrayList<String> sheets = new ArrayList<>();
        predictSecondaryStructure(aminoAcidSequence, helices, sheets);

        // Output predicted structures
        System.out.println("Predicted α-helices: " + helices);
        System.out.println("Predicted β-sheets: " + sheets);
    }

    // Method to initialize the propensity table
    private static void initializePropensityTable() {
        // Populate the table with values (sample values shown here)
        propensityTable.put('A', new double[]{1.42, 0.83});
        propensityTable.put('C', new double[]{0.70, 1.19});
        propensityTable.put('D', new double[]{1.01, 0.54});
        propensityTable.put('E', new double[]{1.51, 0.37});
        propensityTable.put('F', new double[]{1.13, 1.38});
        propensityTable.put('G', new double[]{0.57, 0.75});
        propensityTable.put('H', new double[]{1.00, 0.87});
        propensityTable.put('I', new double[]{1.08, 1.60});
        propensityTable.put('K', new double[]{1.16, 0.74});
        propensityTable.put('L', new double[]{1.34, 1.22});
        propensityTable.put('M', new double[]{1.45, 1.05});
        propensityTable.put('N', new double[]{0.67, 0.89});
        propensityTable.put('P', new double[]{0.57, 0.55});
        propensityTable.put('Q', new double[]{1.11, 1.10});
        propensityTable.put('R', new double[]{0.98, 0.93});
        propensityTable.put('S', new double[]{0.77, 0.75});
        propensityTable.put('T', new double[]{0.83, 1.19});
        propensityTable.put('V', new double[]{1.06, 1.70});
        propensityTable.put('W', new double[]{1.08, 1.37});
        propensityTable.put('Y', new double[]{0.69, 1.47});
    }

    // Method to validate the input amino acid sequence
    private static boolean isValidSequence(String sequence) {
        for (char aminoAcid : sequence.toCharArray()) {
            if (!propensityTable.containsKey(aminoAcid)) {
                return false; // Invalid amino acid found
            }
        }
        return true; // All characters are valid
    }

    // Method to predict secondary structure
    private static void predictSecondaryStructure(String sequence, ArrayList<String> helices, ArrayList<String> sheets) {
        StringBuilder currentHelix = new StringBuilder();
        StringBuilder currentSheet = new StringBuilder();
        
        for (int i = 0; i < sequence.length(); i++) {
            char aminoAcid = sequence.charAt(i);
            double[] propensities = propensityTable.get(aminoAcid);
            if (propensities != null) {
                // Apply thresholds to predict structures
                if (propensities[0] > helixThreshold) {
                    if (currentHelix.length() == 0) {
                        currentHelix.append(i + 1); // Start position
                    }
                } else if (currentHelix.length() > 0) {
                    currentHelix.append("-").append(i); // End position
                    helices.add(currentHelix.toString());
                    currentHelix.setLength(0); // Reset for the next helix
                }

                if (propensities[1] > sheetThreshold) {
                    if (currentSheet.length() == 0) {
                        currentSheet.append(i + 1); // Start position
                    }
                } else if (currentSheet.length() > 0) {
                    currentSheet.append("-").append(i); // End position
                    sheets.add(currentSheet.toString());
                    currentSheet.setLength(0); // Reset for the next sheet
                }
            }
        }
        
        // Check if there are any open structures at the end of the sequence
        if (currentHelix.length() > 0) {
            currentHelix.append("-").append(sequence.length()); // Close last helix
            helices.add(currentHelix.toString());
        }
        if (currentSheet.length() > 0) {
            currentSheet.append("-").append(sequence.length()); // Close last sheet
            sheets.add(currentSheet.toString());
        }
    }
}
