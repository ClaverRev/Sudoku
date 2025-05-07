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
       System.out.println(o) ;
        return grid;
    }

    public static void main(String[] args) {
        try {
            int[][] solution = parseSolution("C://Sat/res3.txt");
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


