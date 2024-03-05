package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de triangle équilatéral (pointant
 * vers le haut par défaut)
 * 
 * @author Jason Xa
 */
public class TriangleEquilateral extends Tuile {
	private static Image image;

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	public TriangleEquilateral(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant le triangle équilatéral
	 * 
	 * @param imageRef l'image représentant le triangle équilatéral
	 */
	public static void setImageRef(Image imageRef) {
		TriangleEquilateral.image = imageRef;
	}

}
