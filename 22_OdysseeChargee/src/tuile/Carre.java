package tuile;

import java.awt.Image;
import java.io.Serializable;

/**
 * Représente l'objet fixe plaçable en forme de carré
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class Carre extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -5637937761263747229L;
	/** l'image représentant un carré */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type carré */
	private static String type = "Carré";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Carre() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public Carre(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Carre(double angleRotation) {
		super(angleRotation, image, type);
	}

	/**
	 * Définit l'image représentant le carré
	 * 
	 * @param imageRef l'image représentant le carré
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Carre.image = imageRef;
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * 
	 * @return Une chaine indiquant que l'objet est un carré
	 */
	// Giroux
	public String toString() {
		return "Carre ";
	}
}
