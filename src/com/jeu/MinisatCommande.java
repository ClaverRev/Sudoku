package com.jeu;
import java.io.*;

public class MinisatCommande {
    public static void main(String[] args) throws IOException {
        String minisatPath = "chemin_vers_minisat"; 
        String inputFile = "sudoku.cnf"; // le nom du fichier à modifier
        String outputFile = "sudoku.out";  // pareil à modifier 

        ProcessBuilder pb = new ProcessBuilder(minisatPath, inputFile, outputFile);
        @SuppressWarnings("unused")
		Process process = pb.start();

        
    }
}