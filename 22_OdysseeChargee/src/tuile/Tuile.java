package tuile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import utilis.Dessinable;
import utilis.OutilsImage;

/**
 * Représente tout objet fixe qui peut être placé dans le mode éditeur.
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class Tuile extends OutilsImage implements Dessinable {

	/** l'abscisse gauche de la tuile (px) */
	protected int x;
	/** l'ordonnée supérieure la tuile en (px) */
	protected int y;
	/** l'angle de rotation de la tuile en (rad) */
	protected double angleRotation;
	/** Détermine si la tuile est un drapeau **/
	boolean drapeau = false;

	/** l'image représentant la tuile */
	private Image image;
	/** Image redimensionnée de la tuile **/
	protected Image imageRedi;
	/** Hauteur de l'image redimensionnée **/
	int largeurImage;
	/** Largeur de l'image redimensionnée **/
	int hauteurImage;

	protected String type;

	protected static int largeurTuile;
	protected static int hauteurTuile;

	/**
	 * Constructeur
	 * 
	 * @param image l'image représentant la tuile
	 * 
	 */
	// Jason Xa
	public Tuile(Image image, String type) {
		x = 0;
		y = 0;
		this.image = image;
		this.type = type;
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
	public Tuile(Image image, int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Constructeur qui copie une tuile
	 * 
	 * @param tuileCopier la tuile à copier
	 */
	// Giroux
	public Tuile(Tuile tuileCopier) {
		this.x = tuileCopier.x;
		this.y = tuileCopier.y;
		this.image = tuileCopier.image;
	}// Fin méthode

	public Tuile(double angleRotation, Image image, String type) {
		this.angleRotation = angleRotation;
		this.image = image;
		this.type = type;
	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées
	 */
	// Jason Xa
	public void dessiner(Graphics2D g2d) {
		AffineTransform transformationAffine = g2d.getTransform();
		g2d.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		g2d.drawImage(image, x, y, null);
		g2d.setTransform(transformationAffine);
	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées
	 * 
	 * @param g2d
	 * @param x
	 * @param y
	 */
	// Jason Xa
	public void dessiner(Graphics2D g2d, int x, int y) {
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
	 * @param d le nouvel angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public void setAngleRotation(double d) {
		this.angleRotation = d;
	}

	/**
	 * Méthode qui met le champ drapeau à vrai
	 */
	// Giroux
	public void setDrapeau() {
		this.drapeau = true;
	}

	/**
	 * Méthode qui indique si c'est un drapeau
	 * 
	 * @return la valeure du boolean drapaeau
	 */
	// Giroux
	public boolean getDrapeau() {
		if (drapeau == true) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * Méthode qui change l'image actuelle par une nouvelle
	 * 
	 * @param nouvImage la nouvelle image
	 */
	// Giroux
	private void setImage(Image nouvImage) {
		this.image = nouvImage;
	}

	/**
	 * Méthode qui change la dimension de l'image
	 * 
	 * @param hauteurImage l'hauteur voulue de l'image
	 * @param largeurImage la largeur voulue de l'image
	 */
	// Giroux
	public void redimensionnerImage(int hauteurImage, int largeurImage) {
		imageRedi = image.getScaledInstance(largeurImage, hauteurImage, Image.SCALE_DEFAULT);
		setImage(imageRedi);

	}

	/**
	 * @return the angleRotation
	 */
	public double getAngleRotation() {
		return angleRotation;
	}

	/**
	 * @param largeurTuile the largeurTuile to set
	 */
	public static void setLargeurTuile(int largeurTuile) {
		Tuile.largeurTuile = largeurTuile;
	}

	/**
	 * @param hauteurTuile the hauteurTuile to set
	 */
	public static void setHauteurTuile(int hauteurTuile) {
		Tuile.hauteurTuile = hauteurTuile;
	}

}
