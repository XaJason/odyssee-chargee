package tuile;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Représente l'objet fixe interactif plaçable qui agit comme une plaque de pics
 * (au bas de la tuile par défaut)
 * 
 * @author Jason Xa
 */
public class Pics extends Tuile {
	private static Image image;
	/**
	 * Constructeur
	 * 
	 */
	public Pics() {
		super(image);
	}
	/**
	 * Constructeur
	 * 
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 * 
	 */
	public Pics(int x, int y) {
		super(image, x, y);
	}

	/**
	 * Définit l'image représentant les pics
	 * 
	 * @param imageRef l'image représentant les pics
	 */
	public static void setImageRef(Image imageRef) {
		Pics.image = imageRef;
	}

	/**
	 * Dessine l'image représentant les pics selon ses coordonnées
	 */
	public void dessiner(Graphics2D g2d) {
		g2d.drawImage(image, x, y + image.getHeight(null), null);
	}

}
