package com.jeu;
import java.io.*;

public class MinisatCommande {
    public static void main(String[] args) throws IOException {
        String minisatPath = "chemin_vers_minisat"; 
        String inputFile = "sudoku.cnf";
        String outputFile = "sudoku.out";

        ProcessBuilder pb = new ProcessBuilder(minisatPath, inputFile, outputFile);
        @SuppressWarnings("unused")
		Process process = pb.start();

        
    }
}