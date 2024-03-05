package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de triangle rectangle (coin en bas
 * à gauche par défaut)
 */
// Jason Xa
public class TriangleRectangle extends Tuile {
	private static Image image;

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	public TriangleRectangle(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant le triangle rectangle
	 * 
	 * @param imageRef l'image représentant le triangle rectangle
	 */
	public static void setImageRef(Image imageRef) {
		TriangleRectangle.image = imageRef;
	}

}
