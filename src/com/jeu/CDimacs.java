package com.jeu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CDimacs {

    public static  int var(int i, int j, int k) {
        return ((i - 1) * 81 + (j - 1) * 9 + k );
    }

    public static void generateDimacs(int[][] grid, String filename) throws IOException {
    	
    
    	
        BufferedWriter writer = new BufferedWriter(new FileWriter("C://Sat/temporaire.txt"));

        int nb = 0;
       
        // Chaque cellule doit contenir un chiffre (Au moins un chiffre)
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 1; k <= 9; k++) {
                	
                    writer.write(var(i, j, k) + " ");
                    
                }
                writer.write("0\n");
                nb++;
            }
        }

        // Chaque cellule ne peut contenir qu'Au plus un chiffre)
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 1; k <= 9; k++) {
                    for (int l = k + 1; l <= 9; l++) {
                        writer.write(-var(i, j, k) + " " + -var(i, j, l) + " 0\n");
                        nb++;
                    }
                }
            }
        }

        // Contraintes sur les lignes
        for (int k = 1; k <= 9; k++) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    for (int m = j + 1; m <= 9; m++) {
                        writer.write(-var(i, j, k) + " " + -var(i, m, k) + " 0\n");
                        nb++;
                    }
                }
            }
        }

        // Contraintes sur les colonnes
        // en gros pareil sauf qu'on change l'usage de i et j 
        for (int k = 1; k <= 9; k++) {
            for (int j = 1; j <= 9; j++) {
                for (int i = 1; i <= 9; i++) {
                    for (int m = i + 1; m <= 9; m++) {
                        writer.write(-var(i, j, k) + " " + -var(m, j, k) + " 0\n");
                        nb++;
                    }
                }
            }
        }

        // Contraintes sur les sous-grilles
        for (int k = 1; k <= 9; k++) {                   
            for (int blockRow = 0; blockRow < 3; blockRow++) {       
                for (int blockCol = 0; blockCol < 3; blockCol++) {   
                    // Parcours de toutes les cellules (i,j) de la sous-grille
                    for (int i = 1; i <= 3; i++) {                   
                        for (int j = 1; j <= 3; j++) {              
                            // Coordonnées absolues dans la grille
                            int row1 = blockRow * 3 + i;
                            int col1 = blockCol * 3 + j;
                            // Comparaison avec les autres cellules (m,n) de la même sous-grille
                            for (int m = 1; m <= 3; m++) {
                                for (int n = 1; n <= 3; n++) {
                                    int row2 = blockRow * 3 + m;
                                    int col2 = blockCol * 3 + n;
                                    // ici c'est pour Éviter les doublons et les paires redondantes
                                    if ((row1 < row2) || (row1 == row2 && col1 < col2)) {
                                        writer.write(-var(row1, col1, k) + " " + -var(row2, col2, k) + " 0\n");
                                        nb++ ;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Ajout des chiffres prédéfinis de notre grille non résolue
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    writer.write(var(i + 1, j + 1, grid[i][j]) + " 0\n");
                    nb++;
                }
            }
        }    writer.close();
        

        Fichier.afficheFichier("temporaire.txt");
        // Ajout de l'entête DIMACS 
        // on le fait en 2 temps pour l'instant car il nous faudrait le nb ; on a une autre solution utilisant des listes 
        //et une autre qui utilise une représentation chainée mais un problème persiste 
      
       BufferedReader read =new BufferedReader(new FileReader ("temporaire.txt")) ;
       BufferedWriter Writer = new BufferedWriter(new FileWriter(filename));
       Writer.write("p cnf 729 " + nb + "\n");
       String ligne ;
       while((ligne=read.readLine()) !=null) {
    	   Writer.write((ligne).trim()+ "\n");
       }
       
        Writer.close();
        read.close();
       
    }

    public static void main(String[] args) throws IOException {
    	/* en 1er param le fichier de la grille et en 2eme le lieu voulu du fichier Dimacs
    	 * 
    	 */
    	BufferedReader reader =new BufferedReader (new FileReader (args[0])) ;
    	String ligne ;
    	int[][] grid =new int [9][9];
    	int gr=0 ;
    	while ((ligne=reader.readLine())!=null) {
    		String [] val =ligne.split(" ") ;
    		for (int g =0 ;g <9 ;g++) {
    				grid [gr][g] = Integer.parseInt(val [g]); 	
    		}
    		gr++;
    	}
    	reader.close();
        
        try {
            generateDimacs(grid, args[1]);
            System.out.println("Fichier DIMACS généré avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

