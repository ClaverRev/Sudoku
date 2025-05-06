package com.jeu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter; 
import java.io.FileWriter; 

public class SAT3 {
    
    public static void transformSATto3SAT(String fichier ) throws IOException {
    	int nb =0 ;
        int ind = 1; // juste pour les variables auxiliaires
        BufferedWriter writer =new BufferedWriter (new FileWriter("C://Sat/temporaire3sat.txt")) ;

        for (String[] clause : clauses("C://Sat/temporaire.txt")) {
            int k = clause.length;
            
            if (k == 2) {
                String y1 = "y" + ind++;
                String y2 = "y" + ind++;
                writer.write(clause[0] + " " + y1 + " " + y2+" " +" 0\n" );
                writer.write(clause[0] + " " +"-" + y1 + " " + y2+" " +" 0\n");
                writer.write(clause[0] + " " + y1 + " " +"-" + y2+" " +" 0\n");
                writer.write(clause[0] + " " + "-" +y1 + " " +"-" + y2 +" " +" 0\n" );
                nb =nb+4 ;
               
            } else if (k == 3) {
                String y1 = "y" + ind++;
                writer.write( clause[0] + " " + clause[1] + " " + y1+" " + "0\n");
                writer.write( clause[0] + " " + clause[1] + " " +"-"+ y1+" " + "0\n");
                nb=nb+2 ;
            } else if (k == 4) {
                writer.write( clause[0] + " " + clause[1] + " " + clause[2]+" " + "0\n");
                nb++ ;
            } else {
                String pre = "y" + ind++;
                writer.write( clause[0] + " " + clause[1] + " " + pre+" " + "0\n");
                nb++ ;

                for (int i = 2; i < k - 1; i++) {
                    String newi = "y" + ind++;
                    writer.write( pre + " " + clause[i] + " " + newi+" " + "0\n");
                    nb++ ;
                    pre = newi;
                }
                
                writer.write( pre + " " + clause[k - 1] + " " + clause[k - 2]  +" "+"0\n");
                nb++ ;
            }
            
        }
        writer.close();
        
        BufferedWriter writ =new BufferedWriter (new FileWriter(fichier)) ;
        BufferedReader read =new BufferedReader(new FileReader ("C://Sat/temporaire3sat.txt")) ;
       
        writ.write("p cnf 729 " + nb + "\n");
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
			transformSATto3SAT("C://Sat/sudoku2.cnf");
			double duration = System.nanoTime() - startTime;
			 double tempsecond=(duration/1000000000);
			 System.out.println("la transformation a pris "+tempsecond+"secondes");
			System.out.println("Fichier 3Sat généré!") ;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

