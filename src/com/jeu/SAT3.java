package com.jeu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter; 
import java.io.FileWriter; 

public class SAT3 {
	public static String fich ;
	
	 public static  int var(int i, int j, int k) {
	        return ((i - 1) * 81 + (j - 1) * 9 + k );
	   }
	 
	 public static void generateDimacs(int[][] grid) throws IOException {
	    	
	        BufferedWriter writer = new BufferedWriter(new FileWriter("temporaire.txt"));

	        // Chaque cellule doit contenir un chiffre (Au moins un chiffre)
	        for (int i = 1; i <= 9; i++) {
	            for (int j = 1; j <= 9; j++) {
	                for (int k = 1; k <= 9; k++) {
	                	
	                    writer.write(var(i, j, k) + " ");
	                    
	                }
	                writer.write("0\n");
	            
	            }
	        }

	        // Chaque cellule ne peut contenir qu'Au plus un chiffre)
	        for (int i = 1; i <= 9; i++) {
	            for (int j = 1; j <= 9; j++) {
	                for (int k = 1; k <= 9; k++) {
	                    for (int l = k + 1; l <= 9; l++) {
	                        writer.write(-var(i, j, k) + " " + -var(i, j, l) + " 0\n");
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
	                    
	                }
	            }
	        }    writer.close();
	        

	      //  Fichier.afficheFichier("temporaire.txt");
	       
	      
	    }
    
     public static void transformSATto3SAT(String fichier ) throws IOException {
    	//CDimacs c=new CDimacs () ;
    	 BufferedReader reader =new BufferedReader (new FileReader (fich)) ;
     	String lign ;
     	int[][] grid =new int [9][9];
     	int gr=0 ;
     	while ((lign=reader.readLine())!=null) {
     		String [] val =lign.split(" ") ;
     		for (int g =0 ;g <=9 ;g++) {
     				grid [gr][g] = Integer.parseInt(val [g]); 	
     		}
     		gr++;
     	}
     	reader.close();
    	generateDimacs(grid);
    	int nb =0 ; // pour le nbre de clauses 
    	int x=0 ; //  pour le nbre de variables 
        int ind = 1; // juste pour les variables auxiliaires
        BufferedWriter writer =new BufferedWriter (new FileWriter("temporaire3sat.txt")) ;

        for (String[] clause : clauses("temporaire.txt")) {
            int k = clause.length;
            
            if (k == 2) {
            	// on fait k+1 car on compte aussi le 0 respectant la convention n-Sat
            	// il sera oublié pour notre propre sat-Solveur 
       // on choisit volontairement de mettre les variables auxiliaires à partir de 50000 pour empecher toute confusion avec les vraies variables
            	int y1 =50000 +ind++ ;
            	int y2 =50000 +ind++ ;
                writer.write(clause[0] + " " + y1 + " " + y2+" " +" 0\n" );
                writer.write(clause[0] + " " +"-" + y1 + " " + y2+" " +" 0\n");
                writer.write(clause[0] + " " + y1 + " " +"-" + y2+" " +" 0\n");
                writer.write(clause[0] + " " + "-" +y1 + " " +"-" + y2 +" " +" 0\n" );
                nb =nb+4 ;
                x=x+5 ;
               
            } else if (k == 3) {
               
                int y1 =50000 +ind++ ;
                writer.write( clause[0] + " " + clause[1] + " " + y1+" " + "0\n");
                writer.write( clause[0] + " " + clause[1] + " " +"-"+ y1+" " + "0\n");
                nb=nb+2 ;
                x=x+4 ;
            } else if (k == 4) {
                writer.write( clause[0] + " " + clause[1] + " " + clause[2]+" " + "0\n");
                nb++ ;
                x=x+3 ;
            } else {
            	int pre =50000 +ind ;
               
                writer.write( clause[0] + " " + clause[1] + " " + pre+" " + "0\n");
                nb++ ;
                x++;

                for (int i = 2; i < k - 1; i++) {
                	int newi =50000 +ind++ ;
                  
                    writer.write( pre + " " + clause[i] + " " + newi+" " + "0\n");
                    nb++ ;
                    x=x+1;
                    pre = newi;
                }
                
                writer.write( pre + " " + clause[k - 1] + " " + clause[k - 2]  +" "+"0\n");
                nb++ ;
                
            }
            
        }
        writer.close();
        
        BufferedWriter writ =new BufferedWriter (new FileWriter(fichier)) ;
        BufferedReader read =new BufferedReader(new FileReader ("temporaire3sat.txt")) ;
       
        writ.write("p cnf "+ x +" " + nb + "\n");
        String ligne ;
        while((ligne=read.readLine()) !=null) {
     	   writ.write((ligne).trim()+ "\n");
        }
        
         writ.close();
         read.close();
        
        
    }

    public static  String[][] clauses (String f) throws IOException {
    	String tab[][] =new String [100000][];
    	BufferedReader reader = new BufferedReader (new FileReader(f));
    	String ligne ;
    	int k=0 ;
    	while ((ligne=reader.readLine())!=null) {
    		tab [k] =ligne.split(" ") ;
    		k=k+1;
    		
    	}
    	reader.close();
    	// puisqu 'il y a des null qui s'inserent et ca n'arrange pas du tout 
    	String [][] ftab =new String[k] [];
    	System.arraycopy(tab,0,ftab,0,k) ;
    	return ftab ;
    		
    }
    
    
    
    
    
    public static void main(String[] args) throws IOException {
   
    	final long startTime = System.nanoTime();
        try {
        	args[0] =fich ;
			transformSATto3SAT(args[1]);
			double duration = System.nanoTime() - startTime;
			 double tempsecond=(duration/1000000000);
			 System.out.println("la transformation a pris "+tempsecond+"secondes");
			System.out.println("Fichier 3Sat généré!") ;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

