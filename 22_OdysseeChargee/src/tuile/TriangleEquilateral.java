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
	/** chaine de caractères représentant la tuile de type triangle équilatéral */
	private static String type = "Triangle équilatéral";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public TriangleEquilateral() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public TriangleEquilateral(double angleRotation) {
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
	public TriangleEquilateral(int x, int y) {
		super(image, x, y, type);
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
