package interactif;

import physique.Vecteur2D;
import tuile.Bloc;

public class Vaisseau extends InteractifPhysique {

	// PROPRIÉTÉS //
	/** Vitesse du vaisseau **/
	private Vecteur2D vitesse;
	
	/** Accélération du vaisseau **/
	private Vecteur2D accel;
	
	/** Charge du vaisseau **/
	private double charge;
	
	/** Rayon du vaisseau **/
	private double rayon;
	
	// CONSTRUCTEUR //
	/**
	 * Constructeur du vaisseau
	 * @param position
	 * @param charge
	 * @param rayon
	 */
	public Vaisseau(Vecteur2D position, Vecteur2D vitesse, Vecteur2D accel, double charge, double rayon) {
		super(position);
		this.vitesse = vitesse;
		this.accel = accel;
		this.charge = charge;
		this.rayon = rayon;
	}
	
	// GETTERS ET SETTERS //
	/**
	 * Retourne la vitesse du vaisseau
	 * @return La vitesse du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getVitesse() {
		return vitesse;
	}

	/**
	 * Modifie la vitesse du vaisseau
	 * @param vitesse Vitesse du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public void setVitesse(Vecteur2D vitesse) {
		this.vitesse = vitesse;
	}
	
	/**
	 * Retourne l'accélération du vaisseau
	 * @return L'accélération du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getAccel() {
		return accel;
	}

	/**
	 * Modifie l'accélération du vaisseau
	 * @param accel Accélération du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public void setAccel(Vecteur2D accel) {
		this.accel = accel;
	}
	
	/**
	 * Retourne la charge du vaisseau
	 * @return La charge du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public double getCharge() {
		return charge;
	}

	/**
	 * Modifie la charge du vaisseau
	 * @param charge Charge du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public void setCharge(double charge) {
		this.charge = charge;
	}
	
	/**
	 * Retourne le rayon du vaisseau
	 * @return Le rayon du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public double getRayon() {
		return rayon;
	}

	/**
	 * Modifie le rayon du vaisseau
	 * @param rayon Rayon du vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public void setRayon(double rayon) {
		this.rayon = rayon;
	}
}
