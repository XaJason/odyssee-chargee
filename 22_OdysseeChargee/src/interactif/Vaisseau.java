package interactif;

import java.awt.Graphics2D;

import physique.Vecteur2D;

public class Vaisseau extends InteractifPhysique {

	// PROPRIÉTÉS //
	/** Vitesse du vaisseau **/
	private Vecteur2D vitesse;

	/** Accélération du vaisseau **/
	private Vecteur2D accel;
	
	/** Rayon du vaisseau **/
	private double rayon;
	
	
	// CONSTRUCTEUR //
	/**
	 * Constructeur du vaisseau
	 * 
	 * @param position
	 * @param vitesse
	 * @param accel
	 * @param rayon
	 * @param charge
	 * @param masse
	 */
	//Enuel René Valentin Kizozo Izia
	public Vaisseau(Vecteur2D position, Vecteur2D vitesse, Vecteur2D accel, double rayon, double charge, double masse) {
		super(position, charge, masse);
		this.vitesse = vitesse;
		this.accel = accel;
		this.rayon = rayon;
	}
	
	
	// SOUS-PROGRAMMES //
	/**
	 * Permet de dessiner un vaisseau, sur le contexte graphique passé en parametre.
	 * @param g2d
	 */
	//Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {	
	}


	// GETTERS ET SETTERS //
	/**
	 * Retourne la vitesse du vaisseau
	 * 
	 * @return La vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getVitesse() {
		return vitesse;
	}

	/**
	 * Modifie la vitesse du vaisseau
	 * 
	 * @param vitesse Vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setVitesse(Vecteur2D vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * Retourne l'accélération du vaisseau
	 * 
	 * @return L'accélération du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getAccel() {
		return accel;
	}

	/**
	 * Modifie l'accélération du vaisseau
	 * 
	 * @param accel Accélération du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setAccel(Vecteur2D accel) {
		this.accel = accel;
	}

	/**
	 * Retourne le rayon du vaisseau
	 * 
	 * @return Le rayon du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getRayon() {
		return rayon;
	}

	/**
	 * Modifie le rayon du vaisseau
	 * 
	 * @param rayon Rayon du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setRayon(double rayon) {
		this.rayon = rayon;
	}
	
}
