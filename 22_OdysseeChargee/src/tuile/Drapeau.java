 package tuile;

import java.awt.Image;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme un drapeau
 * d'arrivée
 * 
 * @author Jason Xa
 */
public class Drapeau extends Tuile {
	private static Image image;

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	public Drapeau(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant le drapeau d'arrivée
	 * 
	 * @param imageRef l'image représentant drapeau d'arrivée
	 */
	public static void setImageRef(Image imageRef) {
		Drapeau.image = imageRef;
	}

}
