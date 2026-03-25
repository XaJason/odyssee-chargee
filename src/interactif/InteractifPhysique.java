package interactif;

import java.awt.Graphics2D;

import physique.Vecteur2D;

/**
 * Classe intéractif physique : classe mères de tous les objets ayant des
 * propriétés physiques et intéragissant physiquement entre eux
 *
 * @author Enuel René Valentin Kizozo Izia
 */
public abstract class InteractifPhysique {

	/** Charge de l'objet intéractif physique **/
	private double charge = 20;

	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre = 1;

	/** Position de l'objet intéractif physique **/
	private Vecteur2D position = new Vecteur2D(-30, -30); // Placée par défaut à l'extérieur du composant pour ne pas le
															// voir

	/**
	 * Constructeur de l'objet intéractif physique Ne prend que sa charge en
	 * paramètre
	 *
	 * @param charge La charge de l'objet intéractif
	 */
	// Enuel René Valentin Kizozo Izia
	public InteractifPhysique(double charge) {
		this.charge = charge;
	}

	/**
	 * Constructeur de l'objet intéractif physique Prend en paramètre sa position et
	 * sa charge
	 *
	 * @param position La position de l'objet intéractif
	 * @param charge   La charge de l'objet intéractif
	 */
	// Enuel René Valentin Kizozo Izia
	public InteractifPhysique(Vecteur2D position, double charge) {
		this.position = new Vecteur2D(position);
		this.charge = charge;
	}

	/**
	 * Permet de créer la géométrie de l'objet intéractif physique.
	 */
	// Enuel René Valentin Kizozo Izia
	public abstract void creerLaGeometrie();

	/**
	 * Permet de dessiner un objet intéractif physique, sur le contexte graphique
	 * passé en parametre.
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public abstract void dessiner(Graphics2D g2d);

	/**
	 * Retourne la charge de l'objet intéractif physique
	 *
	 * @return La charge de l'objet intéractif physique
	 */
	// Enuel René Valentin Kizozo Izia
	public double getCharge() {
		return charge;
	}

	/**
	 * Retourne le nombre de pixels par mètre
	 *
	 * @return Le nombre de pixels par mètre
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPixelsParMetre() {
		return pixelsParMetre;
	}

	/**
	 * Retourne la position de l'objet intéractif physique
	 *
	 * @return La position de l'objet intéractif physique
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getPosition() {
		return position;
	}

	/**
	 * Modifie la charge de l'objet intéractif physique
	 *
	 * @param charge Charge de l'objet intéractif physique
	 */
	// Enuel René Valentin Kizozo Izia
	public void setCharge(double charge) {
		this.charge = charge;
	}

	/**
	 * Modifier le nombre de pixels par mètre afin de dessiner des objets en unité
	 * physique réelle (en mètre et non en pixels)
	 *
	 * @param pixelsParMetre Le nombre de pixels par metre (rapport)
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}

	/**
	 * Modifie la position de l'objet intéractif physique
	 *
	 * @param position Position de l'objet intéractif physique
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPosition(Vecteur2D position) {
		this.position = new Vecteur2D(position);
		creerLaGeometrie();
	}

}
