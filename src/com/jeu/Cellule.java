package com.jeu;

//import java.io.Serializable;



public class Cellule  {
	
//	private static final long serialVersionUID=1L;
	public int val , indice ;
	public Cellule suiv  ;
	
	
	   public Cellule () {
		   super();
	   }
	    public Cellule ( int valeur ) {
	    	this.val =valeur ;	
	    	this.suiv =null ;
	    }
	    public Cellule ( Cellule cell ) {
	    	this.val = 0 ;	
	    	this.suiv =cell ;
	    }
	    public Cellule (int valeur , Cellule suivant ) {
	    	this.val =valeur ;
	    	this.suiv =suivant ;
	    }
	  
	   
	  
	 public int taille ( Cellule cell) {
		int i=0 ;
		//Cellule pre =cell;
		if (cell==null) {
			return 0 ;
		} else {
		  while (cell!=null) {
			i=i+1 ;
			cell=cell.suiv ;
			
		  }
		  return i ;
			
		}
		 
	 }
}