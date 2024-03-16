package tuile;

import java.awt.Image;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme un portail
 * 
 * @author Jason Xa
 */
public class Portail extends Tuile {
	/** l'image représentant un portail */
	private static Image image;
	/** chaine de caractères représentant la tuile de type portail */
	private static String type = "Portail";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Portail() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Portail(double angleRotation) {
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
	public Portail(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant le portail
	 * 
	 * @param imageRef l'image représentant le portail
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Portail.image = imageRef;
	}

	// Giroux
	public String toString() {
		return "Portail ";
	}

}
