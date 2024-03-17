package tuile;

import java.awt.Image;
import java.io.Serializable;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme un portail
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class Portail extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -3752989336876220183L;
	/** l'image représentant un portail */
	private static transient Image image;
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
	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 */

	// Giroux
	public String toString() {
		return "Portail ";
	}

}
