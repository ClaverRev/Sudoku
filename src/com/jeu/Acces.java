package com.jeu;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Acces  {
     BufferedReader reader;
     String[] w;
    int i;
     String cLine; // ligne courante 

    public Acces(String fName) throws IOException { 
    	//fName pour le nom du fichier
        reader = new BufferedReader(new FileReader(fName));
        this.w = new String[0];
        this.i = 0;
       this.cLine = null; 
    }

   
    public void demarrer() {
        try {
            cLine = reader.readLine();
            if (cLine != null) {
                w = extraire(cLine); // pour extraire les mots de la ligne en question 
                i = 0;
            }else {
            	w= new String [0] ;
            }
        }  catch (IOException f) {
        	f.printStackTrace();
        }
        
    }

    
    public void avancer() {
    	if (i< w.length) {
    		 i++;	
    	}else  {
            try {
                cLine = reader.readLine();
                if (cLine != null) {
                    w = extraire(cLine);
                    i = 0;
                } else {
                    w = new String[0]; // on est à la Fin du fichier 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   
    public boolean finDeSequence() {
        return (cLine == null && i >= w.length) ;
    }

    
    public String elementCourant() {
    	 if (i < w.length) {
             return w[i];
         }
         return " " ; //on le suppprimera plus tard

    }

    private String[] extraire(String line) {
    	
    	String[] s = new String[line.length()];

        int i = 0;
        while (i < line.length()) {
            char c = line.charAt(i);
            if (c == ' ' || c == ',' || c == ';' || c == '?' || c == '.' ||  c == ':' ||  c == '\'' ||  c == '?' ||  c == '\"' ||
                c == '-' || c == '_' || c == '=' || c == ')' || c == '(' ||
                c == '&' || c == '*' || c == '/' || c == '+' || c == '@' ||
                c == '"' || c == '[' || c == ']' || c == '<' || c == '>') {
                s[i] = " ";
            } else {
                s[i] = String.valueOf(c);
            }
            i++;
        }

        
        String[] f = new String[line.length()];

        // Remplir le tableau final
        int m = 0;
        int n = 0;
        boolean isWord = false; // isWord pour voir si avant il y avait un mot present 
        while (m < s.length) {
            if (!s[m].equals(" ")) {
                if (!isWord) {
                    isWord = true;
                    if (n != 0) {
                        f[n] = "";
                    }
                }
                if (f[n] == null) {
                    f[n] = s[m];
                } else {
                    f[n] += s[m];
                }
            } else {
                if (isWord) {
                    n++;
                    isWord = false;
                }
            }
            m++;
        }

        if (isWord) {
            n++;
        }

        // Redimensionner le tableau final `f` pour enlever les éléments `null`
         String[] result = new String[n];
         System.arraycopy(f, 0, result, 0, n);
        
         return result;
    }
}