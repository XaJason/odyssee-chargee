package tuile;

import java.awt.Image;
import java.io.Serializable;

/**
 * Représente l'objet fixe plaçable en forme de triangle équilatéral (pointant
 * vers le haut par défaut)
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class TriangleEquilateral extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 2766075546438030099L;
	/** l'image représentant un triangle équilatéral */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type triangle équilatéral */
	private static String type = "Triangle équilatéral";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public TriangleEquilateral() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public TriangleEquilateral(double angleRotation) {
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
	public TriangleEquilateral(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant le triangle équilatéral
	 * 
	 * @param imageRef l'image représentant le triangle équilatéral
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		TriangleEquilateral.image = imageRef;
	}
	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 */

	// Giroux
	public String toString() {
		return "Triangle équilatéral ";
	}

}
