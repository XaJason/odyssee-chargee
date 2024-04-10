package tuile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;

/**
 * Représente l'objet dynamique plaçable unique agissant comme un vaisseau
 * 
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 */
public class VaisseauTuile extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 5393283819635096303L;
	/** l'image représentant un triangle rectangle */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type vaisseau */
	private static String type = "Vaisseau";
	// Coins du carré//
	/** position du x pour délimiter les points **/
	private double xActuel;
	/** position du y pour délimiter les points **/
	private double yActuel;
	/** Coin haut-droit **/
	private Point2D.Double coinHautDroit;
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin bas-gauche **/
	private Double coinBasGauche;

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public VaisseauTuile() {
		super(image, type);
		setVaisseau();
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public VaisseauTuile(double angleRotation) {
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
	public VaisseauTuile(int x, int y) {
		super(image, x, y, type);
		setVaisseau();
	}

	/**
	 * Retourne l'image représentant le vaisseau
	 * 
	 * @return L'image représentant le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le vaisseau
	 * 
	 * @param fichierImage    Le fichier de l'image représentant le vaisseau
	 * @param largeurVaisseau La largeur du vaisseau
	 * @param hauteurVaisseau La hauteur du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurVaisseau, int hauteurVaisseau) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurVaisseau, hauteurVaisseau);
	}

	/**
	 * Dessine l'image représentant le vaisseau selon ses coordonnées
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {
		creerGeometrieContour();
		g2d.setColor(Color.red);
		g2d.draw(contour);
		AffineTransform transformationAffine = g2d.getTransform();
		g2d.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		g2d.drawImage(image, (int) (x + largeurDemiTuile / 2.0), (int) (y + largeurDemiTuile), null);
		g2d.setTransform(transformationAffine);
	}

	/**
	 * Dessine l'image représentant le vaisseau à un emplacement précis
	 * 
	 * @param g2d contexte graphique
	 * @param x   abscisse gauche de la tuile (px)
	 * @param y   ordonnée supérieure de la tuile (px)
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d, int x, int y) {
		g2d.drawImage(image, x, y, null);
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * 
	 * @return Une chaine indiquant que l'objet est un vaisseau
	 */
	// Giroux
	public String toString() {
		return "Vaisseau ";
	}

	/**
	 * Méthode qui ajoute les coins du carré dans l'arrayList points
	 */
	// Giroux
	public void setPoint() {
		super.setPoint();
		pointInitial.setLocation(largeurDemiTuile / 2.0, largeurDemiTuile);
		prePointsCoin.add(pointInitial);
		// Deuxième point(HautDroit)
		xActuel = pointInitial.getX() + largeurDemiTuile;
		yActuel = pointInitial.getY();
		coinHautDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(BasDroit)
		yActuel += largeurDemiTuile;
		coinBasDroit = new Point2D.Double(xActuel, yActuel);
		// Quatrième point(BasGauche)
		xActuel -= largeurDemiTuile;
		coinBasGauche = new Point2D.Double(xActuel, yActuel);
		// Ajouter dans l'arrayList
		prePointsCoin.add(coinHautDroit);
		prePointsCoin.add(coinBasDroit);
		prePointsCoin.add(coinBasGauche);
		// Transformer
		for (Point2D.Double i : prePointsCoin) {
			// Prendre le centre
			i.setLocation(i.getX() - largeurDemiTuile / 2, i.getY() - largeurDemiTuile / 2);
			i = rotation.rotationner(i);

			// Repositionner
			i.setLocation(i.getX() + largeurDemiTuile / 2 + x, i.getY() + largeurDemiTuile / 2 + y);
			pointsCoin.add(i);
		}
	}

	/**
	 * Méthode qui forme le vaisseau dans un area
	 * 
	 * @return la forme du vaisseau dans un area
	 */
	// Kitimir Yim
	public Area formeVaisseau() {

		double diametre = Math.min(largeurTuile, hauteurTuile);
		double rayon = diametre / 2.0;

		double centreX = pointInitial.getX() + largeurTuile / 2.0;
		double centreY = pointInitial.getY() + hauteurTuile / 2.0;

		Ellipse2D cercle = new Ellipse2D.Double(centreX - rayon, centreY - rayon, diametre, diametre);

		Area vaisseauArea = new Area(cercle);
		return vaisseauArea;

	}

}
