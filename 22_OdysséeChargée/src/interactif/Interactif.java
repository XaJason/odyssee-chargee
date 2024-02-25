package interactif;

/**
 * Classe qui sert de base aux classes qui vont être dérivés à partir de celle-ci. Elle
 * possède toutes les propriétés et l'encapsulation que les objets interactifs peuvent avoir.
 *@author Giroux
 */ 

public class Interactif {
	//Propriétés des différents objets intéractifs
	/**Position de l'objet dans la grille: (0) est le haut gauche)*/
	private int position;
	/**Charge de l'objet*/
	private double charge;
	/**Détermine si l'objet tue le vaisseau*/
	private boolean meutrier=false;
	
	/** Constructeur de la classe Intéractif, sert de base pour les constructeurs des
	 * classes dérivées
	 * @param position L'emplacement dans le tableau des positions
	 */
	//Giroux
	Interactif(int position) {
		this.position=position;
	}///Fin constructeur
	
	/**
	 * Méthode qui retourne l'emplacement dans le tableau des positions
	 * @return L'emplacement dans le tableau des positions
	 */
	//Giroux
	int  getPosition() {
		return this.position;
	}///Fin méthode
	
	/**
	 * Méthode qui change l'objet d'emplacement dans le tableau des positions
	 * @param nouvPosition Le nouvel emplacement dans le tableau des positions
	 */
	//Giroux
	void setPosition(int nouvPosition) {
		this.position=nouvPosition;
	}///Fin méthode
	
	/**
	 * Méthode qui retourne la charge de l'objet
	 * @return la charge de l'objet
	 */
	//Giroux
	double getCharge(){
		return this.charge;
	}///Fin méthode
	
	/**
	 * Méthode qui change la charge de l'objet
	 * @param nouvCharge La nouvelle charge
	 */
	//Giroux
	void setCharge(double nouvCharge) {
		this.charge=nouvCharge;
	}///Fin méthode
	
	/**
	 * Méthode qui indique si l'objet est meutrier ou pas
	 * @return Vrai si meutrier, non si non-meutrier
	 */
	//Giroux
	boolean getMeutrier() {
		return this.meutrier;
	}///Fin méthode
	
	/**
	 * Méthode qui change le status de l'objet par rapport à s'il est mortel ou non
	 * @param nouvValMeutrier True s'il devient meutrier, false s'il devient non-meutrier
	 */
	//Giroux
	void setMeutrier(boolean nouvValMeutrier) {
		if(nouvValMeutrier) {
			this.meutrier=true;
		} else {
			this.meutrier=false;
		}
	}///Fin méthode
	
	/**
	 * Méthode qui créer une string avec tous les fields
	 * @return La string avec tous les fields
	 */
	//Giroux
	public String toString() {
		return  "La position est : " + this.position + " et il est "+ 
					(this.getMeutrier() ? "meutrier" : "non meutrier" );
	}///Fin méthode
	
	/**
	 * Méthode qui affiche dans la console la string toString
	 */
	//Giroux
	public void afficher() {
		System.out.println(toString());
	}///Fin méthode
	

}///Fin classe
