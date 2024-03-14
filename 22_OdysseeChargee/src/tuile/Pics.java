package tuile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 * Représente l'objet fixe interactif plaçable qui agit comme une plaque de pics
 * (au bas de la tuile par défaut)
 * 
 * @author Jason Xa
 */
public class Pics extends Tuile {
	/** l'image représentant des pics */
	private static Image image;
	private static String type = "Pics";

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Pics() {
		super(image, type);
	}

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
