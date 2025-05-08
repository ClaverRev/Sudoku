package com.jeu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Affichage3Sat {
   
    public static int[][] parseSolution(String filename) throws IOException {
    	
        int[][] grid = new int[9][9];
        int o =0 ;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ligne ;
            while ((ligne = reader.readLine())!= null ) {
                String[] vars = ligne.split(" ");
                for (String varStr : vars) {
                    int var = Integer.parseInt(varStr);
                    if (var > 0) {
                        int k = (var - 1) % 9 + 1;
                        int j = ((var - 1) / 9) % 9 + 1;
                        int i = (var - 1) / 81 + 1;
                        grid[i - 1][j - 1] = k;
                        o++;
                    }
                }
            }
        }
       System.out.println(o) ; // nous permet ici de savoir le nombre de solutions trouvées étant donné que notre code ne fournit pas  
                                // malheureusement toutes les solutions 
        return grid;
    }

    public static void main(String[] args) {
    	/*en argument le fichier résultat du 3-Sat 
    	 * 
    	 */
        try {
            int[][] solution = parseSolution(args[0]);
            for (int[] row : solution) {
                for (int num : row) {
                    System.out.print(num + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


