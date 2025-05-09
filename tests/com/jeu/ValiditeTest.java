package com.jeu;


import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
public class ValiditeTest {
    public static String p ;   
	
	 public static boolean verifierSudoku(int[][] grid) {
	        return verifierLignes(grid) && verifierColonnes(grid) && verifierSousGrilles(grid);
	    }
    
	 
	    // Vérifie que chaque ligne contient des chiffres uniques
	    public static boolean verifierLignes(int[][] grid) {
	        for (int i = 0; i < 9; i++) {
	           
	            for (int j = 0; j < 9; j++) {
	                
	                int n=0 ;
	                while (n!=j) {
	                	if (grid[i][j]==grid[i][n]) {
	                		return false;
	                	}
	                	n++ ;
	                }
	            }
	        }
	        return true;
	    }

	    // Vérifie que chaque colonne contient des chiffres uniques
	    public static boolean verifierColonnes(int[][] grid) {
	        for (int j = 0; j < 9; j++) {
	            
	            for (int i = 0; i < 9; i++) {
	            	int n=0 ;
	                while (n!=i) {
	                	if (grid[i][j]==grid[n][j]) {
	                		return false;
	                	}
	                	n++ ;
	                }
	            }
	        }
	        return true;
	    }

	    // Vérifie que chaque sous-grille 3x3 contient des chiffres uniques
	    public static boolean verifierSousGrilles(int[][] grid) {
	        for (int blocRow = 0; blocRow < 3; blocRow++) {
	            for (int blocCol = 0; blocCol < 3; blocCol++) {
	                HashSet<Integer> seen = new HashSet<>();
	                for (int i = 0; i < 3; i++) {
	                    for (int j = 0; j < 3; j++) {
	                        int valeur = grid[blocRow * 3 + i][blocCol * 3 + j];
	                        if (valeur != 0 && !seen.add(valeur)) { // 0 est ignoré
	                            return false; // Doublon détecté
	                        }
	                    }
	                }
	            }
	        }
	        return true;
	    }

	    
	    @Test
	    public void test () throws IOException {
	    	//verification de la solution avec minisat 
	    	int [][] grille =SolutionAfficheur.parseSolution ("C://Sat/solution.txt") ;
	    	boolean Haha =verifierSudoku (grille) ;
	    	BufferedWriter writer =new BufferedWriter (new FileWriter("temp.txt")) ;
	    	writer.write(" " + Haha);
	    	writer.close();
	    	BufferedReader read = new BufferedReader (new FileReader("temp.txt")) ;
	    	String ligne=read.readLine().trim() ;
	    	read.close();
	    	assertEquals("true" ,ligne) ;
	    	
	    	//verification de la solution avec notre propre at solveur
	    	
	    	int [][] grill =SolutionAfficheur.parseSolution ("C://Sat/solution.txt") ;
	    	boolean Hah =verifierSudoku (grill) ;
	    	BufferedWriter writ =new BufferedWriter (new FileWriter("temp.txt")) ;
	    	writ.write(" " + Hah);
	    	writ.close();
	    	BufferedReader reado = new BufferedReader (new FileReader("temp.txt")) ;
	    	String lign=reado.readLine().trim() ;
	    	reado.close();

	    	
	    	assertEquals("true" ,lign) ;
	    }
	    
	     
	}

	