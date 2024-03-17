package tuile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme une plaque de pics
 * (au bas de la tuile par défaut)
 * 
 * @author Jason Xa
 */
public class Pics extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 2291455130727306069L;
	/** l'image représentant des pics */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type pics */
	private static String type = "Pics";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Pics() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Pics(double angleRotation) {
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
	public Pics(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant les pics
	 * 
	 * @param imageRef l'image représentant les pics
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Pics.image = imageRef;
	}

	/**
	 * Dessine l'image représentant les pics selon ses coordonnées
	 */
	// Jason Xa
	public void dessiner(Graphics2D g2d) {
		AffineTransform transformationAffine = g2d.getTransform();
		g2d.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		g2d.drawImage(image, x, y + image.getHeight(null), null);
		g2d.setTransform(transformationAffine);
	}

	// Giroux
	public String toString() {
		return "Pics ";
	}

}
