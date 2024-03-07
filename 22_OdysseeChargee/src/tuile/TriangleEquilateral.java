package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de triangle équilatéral (pointant
 * vers le haut par défaut)
 * 
 * @author Jason Xa
 */
public class TriangleEquilateral extends Tuile {
	/** l'image représentant un triangle équilatéral */
	private static Image image;

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public TriangleEquilateral() {
		super(image);
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public TriangleEquilateral(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant le triangle équilatéral
	 * 
	 * @param imageRef l'image représentant le triangle équilatéral
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		TriangleEquilateral.image = imageRef;
	}

	// Giroux
	public String toString() {
		return "Triangle équilatéral ";
	}

}
