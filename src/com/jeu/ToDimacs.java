package com.jeu;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToDimacs {
    
    private static int var(int i, int j, int k) {
    	// Affecte à chaque possibilité une valeur unique donc 81*9 possibilités
        return (i - 1) * 81 + (j - 1) * 9 + k;
    }

    public static void generateDimacs(int[][] grid, String filename) throws IOException {
    	Cellule fictif =new Cellule ();
    	Cellule clauses =fictif.suiv ;

        // Application des Règles de base pour chaque cellule
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                // Au moins un chiffre
               
                for (int k = 1; k <= 9; k++) {
                    clauses.val =var(i, j, k);
                    clauses =clauses.suiv ;
                }
                
                // Au plus un chiffre
                for (int k = 1; k <= 9; k++) {	
                    for (int l = k + 1; l <= 9; l++) {
                    	
                        clauses.val =-var(i, j, k) ;
                        clauses =clauses.suiv ;
                        clauses.val =-var(i, j, l);
                        clauses=clauses.suiv ;
                    }
                   
                }
            }
        }

        // Application des Règles pour les lignes, colonnes et sous-grilles 
        
        //contraintes sur  les lignes 
        for (int k = 1; k <= 9; k++) {
            for (int i = 1; i <= 9; i++) {    
                for (int j = 1; j <= 9; j++) {
                	
                    for (int m = j + 1; m <= 9; m++) {
                        clauses.val = -var(i, j, k) ;
                        clauses =clauses.suiv ;
                       clauses.val = -var(i, m, k);
                       clauses=clauses.suiv ;
                    }   
               }
           }
        }

        
        // Contraintes sur les sous grilles
         for (int k = 1; k <= 9; k++) {
                    for (int blockRow = 0; blockRow < 3; blockRow++) {
                        for (int blockCol = 0; blockCol < 3; blockCol++) {
                            for (int i = 1; i <= 3; i++) {
                                for (int j = 1; j <= 3; j++) {
                                    for (int m = j + 1; m <= 3; m++) {
                                        int row1 = blockRow * 3 + i;
                                        int col1 = blockCol * 3 + j;
                                        int col2 = blockCol * 3 + m;
                                        clauses.val=-var(row1, col1, k) ;
                                        clauses=clauses.suiv ;
                                        clauses.val =-var(row1, col2, k);
                                         clauses=clauses.suiv ; 
                                    }
                                }
                            }
                        }
                    }
       }
        
        
        
         
       // Contraintes sur les colonnes 
         //En gros pareil que celles des lignes sauf inversion de i et j ; ca se comprend 
        
       for (int k = 1; k <= 9; k++) {                           
           for (int j = 1; j <= 9; j++) {                       
               for (int i = 1; i <= 9; i++) {                   
               	    
                   for (int m = i + 1; m <= 9; m++) {           
                       clauses.val = -var(i, j, k) ;            
                       clauses =clauses.suiv ;                  
                      clauses.val = -var(i, m, k);              
                      clauses=clauses.suiv ;                    
                   }                                            
              }                                                 
          }                                                     
       }                                                        
      
       
        // Ajout des chiffres prédéfinis
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
            	
                if (grid[i][j] != 0) {
                	clauses.val = var(i + 1, j + 1, grid[i][j]);
                    clauses =clauses.suiv ;
                 
                   
                }
                
            }
        }

        // Écriture dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("p cnf 729 " + clauses.taille(clauses) + "\n");
            Cellule pred =clauses;
            while (pred  !=null) {
            	writer.write(pred.val + " ");
            	pred=clauses.suiv ;
            }
            writer.write("0\n");
            
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