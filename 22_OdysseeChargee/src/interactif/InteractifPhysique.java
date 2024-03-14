package interactif;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import physique.Vecteur2D;

public abstract class InteractifPhysique {

	/** Position de l'objet intéractif physique **/
	private Vecteur2D position;
	
	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre = 1;
	
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
		this.position = new Vecteur2D(position);
		this.charge = charge;
		this.masse = masse;
	}

	
	// SOUS-PROGRAMMES //
	/**
	 * Permet de créer la géométrie de l'objet intéractif physique.
	 */
	public abstract void creerLaGeometrie();
	
	/**
	 * Permet de dessiner un objet intéractif physique, sur le contexte graphique passé en parametre.
	 * @param g2d
	 */
	//Enuel René Valentin Kizozo Izia
	public abstract void dessiner(Graphics2D g2d);

	
	// GETTERS & SETTERS //
	/**
	 * Retourne la position de l'objet intéractif physique
	 * @return La position de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getPosition() {
		return position;
	}
	
	/**
	 * Modifie la position de l'objet intéractif physique
	 * @param position Position de l'objet intéractif physique
	 */
	//Enuel René Valentin Kizozo Izia
	public void setPosition(Vecteur2D position) {
		this.position = new Vecteur2D(position);
		creerLaGeometrie();
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
	
	/**
	 * Retourne le nombre de pixels par mètre
	 * @return Le nombre de pixels par mètre
	 */
	//Enuel René Valentin Kizozo Izia
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}
	
	/**
	 * Modifier le nombre de pixels par mètre afin de dessiner des objets en unité physique réelle (en mètre et non en pixels)
	 * @param pixelsParMetre Le nombre de pixels par metre (rapport)
	 */
	//Enuel René Valentin Kizozo Izia
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}//fin methode setPixelsParMetre

}
