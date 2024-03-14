package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de carré
 * 
 * @author Jason Xa
 */
public class Carre extends Tuile {
	/** l'image représentant un carré */
	private static Image image;
	private static String type = "Carré";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Carre() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public Carre(int x, int y) {
		super(image, x, y, type);
	}

	public Carre(double angleRotation) {
		super(angleRotation, image, type);
	}

	/**
	 * Définit l'image représentant le carré
	 * 
	 * @param imageRef l'image représentant le carré
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Carre.image = imageRef;
	}

	// Giroux
	public String toString() {
		return "Carre ";
	}

}
