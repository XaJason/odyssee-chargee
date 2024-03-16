package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de triangle rectangle (coin en bas
 * à gauche par défaut)
 * 
 * @author Jason Xa
 */
public class TriangleRectangle extends Tuile {
	/** l'image représentant un triangle rectangle */
	private static Image image;
	/** chaine de caractères représentant la tuile de type triangle rectangle */
	private static String type = "Triangle rectangle";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public TriangleRectangle() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public TriangleRectangle(double angleRotation) {
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
	public TriangleRectangle(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant le triangle rectangle
	 * 
	 * @param imageRef l'image représentant le triangle rectangle
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		TriangleRectangle.image = imageRef;
	}

	// Giroux
	public String toString() {
		return "Triangle rectangle ";
	}
}
