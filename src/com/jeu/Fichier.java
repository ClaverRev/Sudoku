package com.jeu;

import java.io.*;
///import java.util.*; 

public class Fichier{

   private static final String NOM_FICH = "toto.txt";


    static public void afficheFichier(String s) {
	String ligne;
	try{
	Reader r = new FileReader(s);
	BufferedReader br = new BufferedReader(r);
	while ( (ligne = br.readLine()) != null) 
	    {
		System.out.println(ligne);
	    }
	r.close();
	}
	catch(Exception e)
	    {
		System.out.println(e);
	    }
	
    }


    static public void ecrireFichier(String f,String s){
	try{
	    Writer w = new FileWriter(f,true);

	    BufferedWriter output = new BufferedWriter(w);
	    output.write(s);
	    output.flush();
	    output.close(); 
	}
	catch(Exception e)
	    {
		System.out.println(e);
	    } 
    }

   static public void creerFichier(String f,String s){
	try{
	    Writer w = new FileWriter(f);
	    BufferedWriter output = new BufferedWriter(w);
	    output.write(s);
	    output.flush();
	    output.close(); 
	}
	catch(Exception e)
	    {
		System.out.println(e);
	    } 
    }


    static public void main(String[] args){
	//afficheFichier(NOM_FICH);
	creerFichier(NOM_FICH,"titi\n");
	ecrireFichier(NOM_FICH,"toto\n");	
	afficheFichier(NOM_FICH);
    }

}