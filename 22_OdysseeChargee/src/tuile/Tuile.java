package tuile;

import java.awt.Graphics2D;
import java.awt.Image;

import utilis.Dessinable;

/**
 * Représente tout objet fixe qui peut être placé dans le mode éditeur.
 * 
 * @author Jason Xa
 */
public class Tuile implements Dessinable {

	/** l'abscisse gauche de la tuile (px) */
	protected int x;
	/** l'ordonnée supérieure la tuile en (px) */
	protected int y;
	/** l'angle de rotation de la tuile en (rad) */
	protected double angleRotation;

	/** l'image représentant la tuile */
	Image image;

	/**
	 * Constructeur
	 * 
	 * @param image l'image représentant la tuile
	 * 
	 */
	// Jason Xa
	public Tuile(Image image) {
		x = 0;
		y = 0;
		this.image = image;
	}

	/**
	 * Constructeur
	 * 
	 * @param image l'image représentant la tuile
	 * @param x     l'abscisse gauche de la tuile (px)
	 * @param y     l'ordonnée supérieure la tuile (px)
	 * 
	 */
	// Jason Xa
	public Tuile(Image image, int x, int y) {
		this.x = x;
		this.y = y;
		this.image = image;
	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées
	 */
	// Jason Xa
	public void dessiner(Graphics2D g2d) {
		g2d.drawImage(image, x, y, null);
	}

	/**
	 * Définit la nouvelle abscisse gauche de la tuile
	 * 
	 * @param x la nouvelle abscisse gauche de la tuile (px)
	 */
	// Jason Xa
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Définit la nouvelle ordonnée supérieure de la tuile
	 * 
	 * @param y la nouvelle ordonnée supérieure (px)
	 */
	// Jason Xa
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Définit le nouvel angle de rotation de la tuile
	 * 
	 * @param angleRotation le nouvel angle de rotation de la tuile (rad)
	 */
	public void setAngleRotation(int angleRotation) {
		this.angleRotation = angleRotation;
	}


}
