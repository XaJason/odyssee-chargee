package tuile;

import java.awt.Image;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme un portail
 * 
 * @author Jason Xa
 */
public class Portail extends Tuile {
	private static Image image;
	/**
	 * Constructeur
	 * 
	 */
	public Portail() {
		super(image);
	}
	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	public Portail(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant le portail
	 * 
	 * @param imageRef l'image représentant le portail
	 */
	public static void setImageRef(Image imageRef) {
		Portail.image = imageRef;
	}

}
