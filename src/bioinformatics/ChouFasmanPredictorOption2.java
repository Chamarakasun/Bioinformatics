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

public class ChouFasmanPredictorOption2 {
    // Propensity tables for each amino acid
    static HashMap<Character, double[]> propensityTable = new HashMap<>();
    static final double helixThreshold = 1.00; // Define a threshold for helices
    static final double sheetThreshold = 1.00; // Define a threshold for sheets
    static final double turnThreshold = 1.00; // Define a threshold for turns

    public static void main(String[] args) {
        // Initialize the propensity table
        initializePropensityTable();

        // Read amino acid sequence from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amino acid sequence: ");
        String aminoAcidSequence = scanner.nextLine();

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
        propensityTable.put('A', new double[]{1.42, 0.83, 0.66});
        propensityTable.put('C', new double[]{0.70, 1.19, 1.19});
        // Add all amino acids...
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

