package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de triangle rectangle (coin en bas
 * à gauche par défaut)
 * 
 * @author Jason Xa
 */
public class Vaisseau extends Tuile {
	/** l'image représentant un triangle rectangle */
	private static Image image;
	private static String type = "Vaisseau";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Vaisseau() {
		super(image, type);
	}

	/**
	 * @param angleRotation
	 */
	public Vaisseau(double angleRotation) {
		super(angleRotation, image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public Vaisseau(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant le triangle rectangle
	 * 
	 * @param imageRef l'image représentant le triangle rectangle
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Vaisseau.image = imageRef;
	}

	// Giroux
	public String toString() {
		return "Triangle rectangle ";
	}
}
