package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de triangle rectangle (coin en bas
 * à gauche par défaut)
 * 
 * @author Jason Xa
 */
public class TriangleRectangle extends Tuile {
	/***/
	private static Image image;

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public TriangleRectangle() {
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
	public TriangleRectangle(int x, int y) {
		super(image, x, y);
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

}
