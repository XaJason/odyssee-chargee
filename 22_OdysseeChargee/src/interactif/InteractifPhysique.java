package interactif;

import java.awt.Graphics2D;

import physique.Vecteur2D;

public class InteractifPhysique {

	/** Position de l'objet intéractif physique **/
	private Vecteur2D position;
	
	/** Charge de l'objet intéractif physique **/
	private double charge;
	
	/** Masse de l'objet intéractif physique **/
	private double masse;

	
	
	
	// CONSTRUCTEUR //
	/**
	 * Constructeur de l'objet intéractif physique
	 * @param position
	 * @param charge
	 * @param masse
	 */
	public InteractifPhysique(Vecteur2D position, double charge, double masse) {
		this.position = position;
		this.charge = charge;
		this.masse = masse;
	}

	// SOUS-PROGRAMMES
	/**
	 * Permet de dessiner un objet intéractif physique, sur le contexte graphique passé en parametre.
	 * @param g2d
	 */
	//Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {	
	}

	
	
	// GETTERS & SETTERS 
	/**
	 * Retourne la position de l'objet intéractif physique
	 * @return La position de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getPosition() {
		return position;
	}
	
	/**
	 * Retourne la charge de l'objet intéractif physique
	 * @return La charge de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public double getCharge() {
		return charge;
	}

	/**
	 * Modifie la charge de l'objet intéractif physique
	 * @param charge Charge de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public void setCharge(double charge) {
		this.charge = charge;
	}
	
	/**
	 * Retourne la masse de l'objet intéractif physique
	 * @return La masse de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public double getMasse() {
		return masse;
	}
	
	/**
	 * Modifie la masse de l'objet intéractif physique
	 * @param masse Masse de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public void setMasse(double masse) {
		this.masse = masse;
	}

}
