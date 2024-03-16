package tuile;

import java.awt.Image;

/**
 * Représente l'objet interactif fixe unique plaçable qui agit comme un drapeau
 * d'arrivée
 * 
 * @author Jason Xa
 */
public class Drapeau extends Tuile {
	/** l'image représentant un drapeau */
	private static Image image;
	/** chaine de caractères représentant la tuile de type drapeau */
	private static String type = "Drapeau";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Drapeau() {
		super(image, type);
		setDrapeau();
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Drapeau(double angleRotation) {
		super(angleRotation, image, type);
		setDrapeau();
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public Drapeau(int x, int y) {
		super(image, x, y, type);
		setDrapeau();
	}

	/**
	 * Définit l'image représentant le drapeau d'arrivée
	 * 
	 * @param imageRef l'image représentant drapeau d'arrivée
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Drapeau.image = imageRef;
	}

	// Giroux
	public String toString() {
		return "Drapeau ";
	}

}
