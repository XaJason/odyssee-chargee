package tuile;

import java.awt.Image;
import java.io.Serializable;

/**
 * Représente l'objet dynamique plaçable unique agissant comme un vaisseau
 * 
 * @author Jason Xa
 */
public class Vaisseau extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 5393283819635096303L;
	/** l'image représentant un triangle rectangle */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type vaisseau */
	private static String type = "Vaisseau";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Vaisseau() {
		super(image, type);
		setVaisseau();
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Vaisseau(double angleRotation) {
		super(angleRotation, image, type);
		setVaisseau();
	}

	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public Vaisseau(int x, int y) {
		super(image, x, y, type);
		setVaisseau();
	}

	/**
	 * Définit l'image représentant le vaisseau
	 * 
	 * @param imageRef l'image représentant le vaisseau
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Vaisseau.image = imageRef;
	}
	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 */

	// Giroux
	public String toString() {
		return "Vaisseau ";
	}
}
