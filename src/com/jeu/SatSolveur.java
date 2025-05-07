package com.jeu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SatSolveur {

    static final int MAX_ITERATION = 1000000;
    static final double P = 0.1; // probabilité du "random walk"

    public static void main(String[] args) throws IOException {
        String fichierDIMACS = "C://Sat/sudoku3sat.cnf"; // <- change ce chemin si besoin

        int[][] clauses = lireFichier(fichierDIMACS);
        int nbVariables = nbVariables(clauses);

        boolean[] assignation = initAssignation(nbVariables);

        boolean satisfiable = walkSAT(clauses, assignation, nbVariables);

        if (satisfiable) {
            System.out.println("SATISFIABLE !");
            afficherSolution(assignation);
        } else {
            System.out.println("PAS DE SOLUTION TROUVÉE.");
        }
    }

    public static int[][] lireFichier(String fichier) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fichier));
        String ligne;
        int nbVariables = 0, nbClauses = 0;

        // Lire l'en-tête
        while ((ligne = reader.readLine()) != null) {
            if (ligne.startsWith("p cnf")) {
                String[] parts = ligne.trim().split("\\s+");
                nbVariables = Integer.parseInt(parts[2]);
                nbClauses = Integer.parseInt(parts[3]);
                break;
            }
        }

        int[][] clauses = new int[nbClauses][3];
        int index = 0;

        while ((ligne = reader.readLine()) != null && index < nbClauses) {
            ligne = ligne.trim();
            if (ligne.isEmpty() || ligne.startsWith("c")) continue;

            String[] lits = ligne.split("\\s+");
            int[] clause = new int[3];
            int j = 0;
            for (String lit : lits) {
                int val = Integer.parseInt(lit);
                if (val == 0) break;
                clause[j++] = val;
            }
            clauses[index++] = clause;
        }

        reader.close();
        return clauses;
    }

    public static int nbVariables(int[][] clauses) {
        int max = 0;
        for (int[] clause : clauses) {
            for (int lit : clause) {
                int abs = Math.abs(lit);
                if (abs > max) max = abs;
            }
        }
        return max;
    }

    public static boolean[] initAssignation(int n) {
        Random rand = new Random();
        boolean[] assignation = new boolean[n + 1]; // index 0 inutilisé
        for (int i = 1; i <= n; i++) {
            assignation[i] = rand.nextBoolean();
        }
        return assignation;
    }

    public static boolean walkSAT(int[][] clauses, boolean[] assignation, int n) {
        Random rand = new Random();

        for (int i = 0; i < MAX_ITERATION; i++) {
            if (satisfaitToutesClauses(clauses, assignation)) return true;

            int[][] clausesFaussees = clausesFaussees(clauses, assignation);
            int[] clause = clausesFaussees[rand.nextInt(clausesFaussees.length)];

            if (rand.nextDouble() < P) {
                // Random walk
                int lit = clause[rand.nextInt(clause.length)];
                int var = Math.abs(lit);
                assignation[var] = !assignation[var];
            } else {
                // Flip qui maximise les clauses satisfaites
                int bestVar = -1;
                int bestScore = -1;

                for (int lit : clause) {
                    int var = Math.abs(lit);
                    assignation[var] = !assignation[var]; // tentative
                    int score = nbClausesSatisfaites(clauses, assignation);
                    if (score > bestScore) {
                        bestScore = score;
                        bestVar = var;
                    }
                    assignation[var] = !assignation[var]; // revert
                }

                if (bestVar != -1) {
                    assignation[bestVar] = !assignation[bestVar];
                }
            }
        }

        return false;
    }

    public static boolean satisfaitToutesClauses(int[][] clauses, boolean[] assignation) {
        for (int[] clause : clauses) {
            boolean clauseSatisfaite = false;
            for (int lit : clause) {
                int var = Math.abs(lit);
                boolean val = assignation[var];
                if ((lit > 0 && val) || (lit < 0 && !val)) {
                    clauseSatisfaite = true;
                    break;
                }
            }
            if (!clauseSatisfaite) return false;
        }
        return true;
    }

    public static int[][] clausesFaussees(int[][] clauses, boolean[] assignation) {
        int[][] temp = new int[clauses.length][3];
        int count = 0;
        for (int[] clause : clauses) {
            boolean clauseSatisfaite = false;
            for (int lit : clause) {
                int var = Math.abs(lit);
                boolean val = assignation[var];
                if ((lit > 0 && val) || (lit < 0 && !val)) {
                    clauseSatisfaite = true;
                    break;
                }
            }
            if (!clauseSatisfaite) {
                temp[count++] = clause;
            }
        }

        int[][] result = new int[count][3];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    public static int nbClausesSatisfaites(int[][] clauses, boolean[] assignation) {
        int count = 0;
        for (int[] clause : clauses) {
            for (int lit : clause) {
                int var = Math.abs(lit);
                boolean val = assignation[var];
                if ((lit > 0 && val) || (lit < 0 && !val)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static void afficherSolution(boolean[] assignation) throws IOException {
    	
    	BufferedWriter Writ = new BufferedWriter ( new FileWriter( "C://Sat/res3.txt")) ;
    	int [] tab = new int [81] ;
    	int k=0 ;
        for (int i = 1; i < assignation.length; i++) {
        	if (i+1 <= 729) {
            System.out.println("Variable " + i + " = " + (assignation[i] ? "Vrai" : "Faux"));
              if (assignation [i]) {
            	  tab[k++] =i ;
            	  Writ.write(i +" ") ;
                
              }
        	}
        }
        Writ.close();
       /*
        for (int v=1 ;v<=729 ;v++) {
        	if (assignation [v] ) {
        		int valeur = (v - 1) % 9 + 1;
                int colonne = ((v - 1) / 9) % 9 + 1;
                int ligne = (v - 1) / 81 + 1;
                System.out.println ("case i j k " +ligne +" "+colonne+" "+valeur) ;
        		
        	}
        }
        */
    }
}

