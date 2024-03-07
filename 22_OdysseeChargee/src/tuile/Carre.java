package tuile;

import java.awt.Image;

/**
 * Représente l'objet fixe plaçable en forme de carré
 * 
 * @author Jason Xa
 */
public class Carre extends Tuile {
	private static Image image;

	/**
	 * Constructeur
	 * 
	 */
	public Carre() {
		super(image);
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	public Carre(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant le carré
	 * 
	 * @param imageRef l'image représentant le carré
	 */
	public static void setImageRef(Image imageRef) {
		Carre.image = imageRef;
	}

	//Giroux
	public String toString() {
		return "Carre ";
	}
	
	

}
