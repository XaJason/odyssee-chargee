package interactif;

/**
@author Giroux
*/ 

public class Interactif {
	//Propriétés des différents objets intéractifs
	/**Position de l'objet dans la grille(0 est le haut gauche)*/
	private int position;
	/**Charge de l'objet*/
	private double charge;
	/**Détermine si l'objet tue le vaisseau*/
	private boolean meutrier=false;
	
	Interactif(int position) {
		this.position=position;
		
	}
	
	
	/**
	 * Méthode qui retourne la charge de l'objet
	 * @return la charge de l'objet
	 */
	double getCharge(){
		return this.charge;
	}
	
	void setCharge(double nouvCharge) {
		this.charge=nouvCharge;
	}
	
	boolean getMeutrier() {
		return this.meutrier;
	}
	
	void setMeutrier(boolean nouvValMeutrier) {
		if(nouvValMeutrier) {
			this.meutrier=true;
		} else {
			this.meutrier=false;
		}
	}
	
	public String toString() {
		return  "this.position" + (this.getMeutrier() ? "Meutrier" : "non meutrier" );
	}
	
	public void afficher() {
		System.out.print(toString());
	}
	

}
