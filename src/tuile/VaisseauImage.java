package tuile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
public class VaisseauImage extends Tuile implements Serializable {
	/** l'image représentant un triangle rectangle */
	private static transient Image image;
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 5393283819635096303L;
	/** chaine de caractères représentant la tuile de type vaisseau */
	private static String type = "Vaisseau";
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin haut-droit **/
	private Point2D.Double coinHautDroit;
	/** Coin bas-gauche **/
	private Double coinHautGauche;

	/** position du x pour délimiter les points **/
	private double xActuel;
	/** position du y pour délimiter les points **/
	private double yActuel;

	/**
	 * Constructeur
	 *
	 */
	// Jason Xa
	public VaisseauImage() {
		super(image, type);
		setVaisseau();
	}

	/**
	 * Constructeur
	 *
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public VaisseauImage(double angleRotation) {
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
	public VaisseauImage(int x, int y) {
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
	 * Dessine l'image représentant le vaisseau selon les coordonnées de sa tuile
	 * dans la grille (fixe)
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		creerGeometrieContour();
		g2dPrive.setColor(Color.red);
		g2dPrive.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		/*
		 * Ajustement d'un paramètre pour dessiner l'image à cause des transformations
		 * du paintComponent de Grille permettant de mettre l'origine en bas à gauche
		 */
		double hauteurTuileImage = -(hauteurTuile / 2.0);
		g2dPrive.drawImage(image, (int) (x + largeurDemiTuile / 2.0), (int) (y + largeurDemiTuile),
				(int) (largeurTuile / 2.0), (int) hauteurTuileImage, null);
	}

	/**
	 * Dessine l'image représentant le vaisseau selon la position de l'objet
	 * Vaisseau lors de l'animation
	 *
	 * @param g2d contexte graphique
	 * @param x   abscisse gauche de la tuile du vaisseau (px)
	 * @param y   ordonnée supérieure de la tuile du vaisseau (px)
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d, double x, double y) {
		double yImage = y + hauteurTuile / 2.0;
		double hauteurTuileImage = -(hauteurTuile / 2.0);
		g2d.drawImage(image, (int) (x), (int) yImage, (int) (largeurTuile / 2.0), (int) hauteurTuileImage, null);
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

	/**
	 * Méthode qui ajoute les coins du carré dans l'arrayList points
	 */
	// Giroux
	@Override
	public void setPoint() {
		super.setPoint();
		prePointsCoin.clear();
		pointsCoin.clear();
		xActuel = 0;
		yActuel = 0;
		xActuel = largeurDemiTuile / 2.0;
		pointInitial.setLocation(xActuel, yActuel);
		prePointsCoin.add(pointInitial);
		// Deuxième point(BasDroit)
		xActuel += largeurDemiTuile;
		coinBasDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(HautDroit)
		yActuel += largeurDemiTuile;
		coinHautDroit = new Point2D.Double(xActuel, yActuel);
		// Quatrième point(HautGauche)
		xActuel -= largeurDemiTuile;
		coinHautGauche = new Point2D.Double(xActuel, yActuel);
		// Ajouter dans l'arrayList
		prePointsCoin.add(coinBasDroit);
		prePointsCoin.add(coinHautDroit);
		prePointsCoin.add(coinHautGauche);
		// Transformer
		for (Point2D.Double i : prePointsCoin) {
			// Prendre le centre
			i.setLocation(i.getX() - largeurTuile / 2, i.getY() - hauteurTuile / 2);
			i = rotation.rotationner(i);

			// Repositionner
			i.setLocation(i.getX() + largeurTuile / 2 + x, i.getY() + hauteurTuile / 2 + y);
			pointsCoin.add(i);
		}
		creerGeometrieContour();
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 *
	 * @return Une chaine indiquant que l'objet est un vaisseau
	 */
	// Giroux
	@Override
	public String toString() {
		return "Vaisseau ";
	}

}
