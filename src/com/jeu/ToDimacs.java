package com.jeu;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToDimacs {
    
    private static int var(int i, int j, int k) {
        return (i - 1) * 81 + (j - 1) * 9 + k;
    }

    public static void generateDimacs(int[][] grid, String filename) throws IOException {
        List<List<Integer>> clauses = new ArrayList<>();

        // Règles de base pour chaque cellule
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                // Au moins un chiffre
                List<Integer> clause = new ArrayList<>();
                for (int k = 1; k <= 9; k++) {
                    clause.add(var(i, j, k));
                }
                clauses.add(clause);

                // Au plus un chiffre
                for (int k = 1; k <= 9; k++) {
                    for (int l = k + 1; l <= 9; l++) {
                        clauses.add(List.of(-var(i, j, k), -var(i, j, l)));
                    }
                }
            }
        }

        // Règles pour les lignes, colonnes et sous-grilles
        for (int k = 1; k <= 9; k++) {
            // Lignes
            for (int i = 1; i <= 9; i++) {
                List<Integer> clause = new ArrayList<>();
                for (int j = 1; j <= 9; j++) {
                    clause.add(var(i, j, k));
                }
                clauses.add(clause);
                
                for (int j = 1; j <= 9; j++) {
                    for (int m = j + 1; m <= 9; m++) {
                        clauses.add(List.of(-var(i, j, k), -var(i, m, k)));
                    }
                }
            }
            
            // Colonnes (similaire aux lignes)
            // Sous-grilles (code complet sur GitHub)
        }

        for (int k = 1; k <= 9; k++) {
            for (int blockRow = 0; blockRow < 3; blockRow++) {
                for (int blockCol = 0; blockCol < 3; blockCol++) {
                    List<Integer> clause = new ArrayList<>();
                    for (int i = 1; i <= 3; i++) {
                        for (int j = 1; j <= 3; j++) {
                            int row = blockRow * 3 + i;
                            int col = blockCol * 3 + j;
                            clause.add(var(row, col, k));
                        }
                    }
                    clauses.add(clause);
                   
                    // Interdiction de répétition dans une sous-grille
                    for (int i = 1; i <= 3; i++) {
                        for (int j = 1; j <= 3; j++) {
                            for (int m = j + 1; m <= 3; m++) {
                                clauses.add(List.of(-var(blockRow * 3 + i, blockCol * 3 + j, k),
                                                    -var(blockRow * 3 + i, blockCol * 3 + m, k)));
                            }
                        }
                    }
                }
            }
        }
        
        
        
        for (int k = 1; k <= 9; k++) {
            for (int j = 1; j <= 9; j++) { // Parcours des colonnes
                List<Integer> clause = new ArrayList<>();
                for (int i = 1; i <= 9; i++) {
                    clause.add(var(i, j, k));
                }
                clauses.add(clause);
               
                // Interdiction de répétition dans une colonne
                for (int i = 1; i <= 9; i++) {
                    for (int m = i + 1; m <= 9; m++) {
                        clauses.add(List.of(-var(i, j, k), -var(m, j, k)));
                    }
                }
            }
        }
        
        
        // Ajout des chiffres prédéfinis
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    clauses.add(List.of(var(i + 1, j + 1, grid[i][j])));
                }
            }
        }

        // Écriture dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("p cnf 729 " + clauses.size() + "\n");
            for (List<Integer> clause : clauses) {
                for (int lit : clause) {
                    writer.write(lit + " ");
                }
                writer.write("0\n");
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };

        try {
            generateDimacs(grid, "C://Sat/sudoku.cnf");
            System.out.println("Fichier DIMACS généré !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}