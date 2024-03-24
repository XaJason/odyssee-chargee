package tuile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import math.MatriceRotation;
import utilitaires.Dessinable;
import utilitaires.OutilsImage;

/**
 * Représente tout objet fixe qui peut être placé dans le mode éditeur.
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class Tuile extends OutilsImage implements Dessinable, Serializable {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -7235372039893162386L;
	/** l'abscisse gauche de la tuile (px) */
	protected int x;
	/** l'ordonnée supérieure la tuile en (px) */
	protected int y;
	/** l'angle de rotation de la tuile en (rad) */
	protected double angleRotation;
	/** Détermine si la tuile est un drapeau **/
	protected boolean drapeau = false;
	/** Détermine si la tuile est un vaisseau **/
	protected boolean vaisseau = false;

	/** l'image représentant la tuile */
	private transient Image image;
	/** Image redimensionnée de la tuile **/
	protected Image imageRedi;

	/** chaine de caractères représentant le type de la tuile */
	protected String type;

	/** largeur de la tuile (px) */
	protected static int largeurTuile;
	/** hauteur de la tuile (px) */
	protected static int hauteurTuile;
	/**
	 * ArrayList qui contient les points des coins des blocs avant d'être transformé
	 **/
	protected ArrayList<Point2D> prePointsCoin = new ArrayList<Point2D>();
	/** ArrayList qui contient les points des coins des blocs post-transformé **/
	protected ArrayList<Point2D> pointsCoin = new ArrayList<Point2D>();
	/** Point initial(haut-gauche) du bloc **/
	protected Point2D pointInitial;
	/** Path qui représente le contour du bloc **/
	protected Path2D.Double contour;
	/** Matrice de rotation **/
	MatriceRotation rotation;

	/**
	 * Constructeur
	 * 
	 * @param image l'image représentant la tuile
	 * @param type  le type de la tuile
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
	 * @param type  le type de la tuile
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
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 * @param image         l'image représentant la tuile
	 * @param type          le type de la tuile
	 */
	// Jason Xa
	public Tuile(double angleRotation, Image image, String type) {
		this.angleRotation = angleRotation;
		this.image = image;
		this.type = type;
	}

	/**
	 * Retourne la chaine de caractères représentant le type de la tuile
	 * 
	 * @return la chaine de caractères représentant le type de la tuile
	 */
	// Jason Xa
	public String getType() {
		return type;
	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Jason Xa

	public void dessiner(Graphics2D g2d) {

		// Jason Xa((Partie ci-dessous)
		AffineTransform transformationAffine = g2d.getTransform();
		g2d.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		g2d.drawImage(image, x, y, null);
		g2d.setTransform(transformationAffine);

		// Giroux(Partie ci-dessous)
		creerGeometrieContour();
		g2d.setColor(Color.red);
		g2d.draw(contour);

	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées
	 * 
	 * @param g2d contexte graphique
	 * @param x   abscisse gauche de la tuile (px)
	 * @param y   ordonnée supérieure de la tuile (px)
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
	protected void setDrapeau() {
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
	 * Méthode qui met le champ vaisseau à vrai
	 */
	// Giroux
	protected void setVaisseau() {
		this.vaisseau = true;
	}

	/**
	 * Méthode qui indique si c'est un vaisseau
	 * 
	 * @return la valeure du boolean drapaeau
	 */
	// Giroux
	public boolean getVaisseau() {
		if (vaisseau == true) {
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
	 * Retourne l'angle de rotation
	 * 
	 * @return the angleRotation l'angle de rotation (rad)
	 */
	// Jason Xa
	public double getAngleRotation() {
		return angleRotation;
	}

	/**
	 * Définit la largeur des tuiles
	 * 
	 * @param largeurTuile la nouvelle largeur des tuiles (px)
	 */
	// Jason Xa
	public static void setLargeurTuile(int largeurTuile) {
		Tuile.largeurTuile = largeurTuile;
	}

	/**
	 * Définit la largeur des tuiles
	 * 
	 * @param hauteurTuile la nouvelle hauteur des tuiles (px)
	 * 
	 */
	// Jason Xa
	public static void setHauteurTuile(int hauteurTuile) {
		Tuile.hauteurTuile = hauteurTuile;
	}

	/**
	 * Méthode à redéfinir dans les sous classes pour mettre les points des coins
	 * dans le arrayList pointsCoin
	 */
	// Giroux
	public void setPoint() {
		pointInitial = new Point2D.Double(0, 0);
		rotation = new MatriceRotation(this.angleRotation);

	}

	/**
	 * Méthode qui instancie le path qui fait le contour du bloc
	 */
	// Giroux
	protected void creerGeometrieContour() {

		contour = new Path2D.Double();
		if (pointsCoin.size() != 0) {
			contour.moveTo(pointsCoin.get(0).getX(), pointsCoin.get(0).getY());
		}
		for (Point2D i : pointsCoin) {
			contour.lineTo(i.getX(), i.getY());
		}
		if (pointsCoin.size() != 0) {
			contour.lineTo(pointsCoin.get(0).getX(), pointsCoin.get(0).getY());
		}

	}
}