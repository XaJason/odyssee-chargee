package tuile;

import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;

/**
 * Représente l'objet interactif fixe unique plaçable qui agit comme un drapeau
 * d'arrivée
 *
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 */
public class Drapeau extends Tuile implements Serializable {
	/** l'image représentant un drapeau */
	private static transient Image image;
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 6952589919609649643L;
	/** chaine de caractères représentant la tuile de type drapeau */
	private static String type = "Drapeau";
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin bas-gauche **/
	private Double coinBasGauche;
	/** Coin haut-droit **/
	private Point2D.Double coinHautDroit;

	/** position du x pour délimiter les points **/
	private double xActuel;
	/** position du y pour délimiter les points **/
	private double yActuel;

	/**
	 * Constructeur
	 *
	 */
	// Jason Xa
	public Drapeau() {
		super(image, type);
		setDrapeau();

	}

	/**
	 * Constructeur
	 *
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Drapeau(double angleRotation) {
		super(angleRotation, image, type);
		setDrapeau();

	}

	/**
	 * Constructeur
	 *
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 *
	 */
	// Jason Xa
	public Drapeau(int x, int y) {
		super(image, x, y, type);
		setDrapeau();

	}

	/**
	 * Retourne l'image représentant le drapeau d'arrivée
	 *
	 * @return L'image représentant le drapeau d'arrivée
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le drapeau d'arrivée
	 *
	 * @param fichierImage Le fichier de l'image représentant le drapeau d'arrivée
	 * @param largeurTuile La largeur de la tuile
	 * @param hauteurTuile La hauteur de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Méthode qui forme le drapeau dans un area
	 *
	 * @return la forme du drapeau dans un area
	 */
	// Kitimir Yim
	@Override
	public Area formerAireObjetSpecial() {
		Area aireDrapeau = new Area(contour);
		return aireDrapeau;
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
		prePointsCoin.add(pointInitial);
		// Deuxième point(HautDroit)
		xActuel = pointInitial.getX() + largeurTuile;
		yActuel = pointInitial.getY();
		coinHautDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(BasDroit)
		yActuel += hauteurTuile;
		coinBasDroit = new Point2D.Double(xActuel, yActuel);
		// Quatrième point(BasGauche)
		xActuel -= largeurTuile;
		coinBasGauche = new Point2D.Double(xActuel, yActuel);
		// Ajouter dans l'arrayList
		prePointsCoin.add(coinHautDroit);
		prePointsCoin.add(coinBasDroit);
		prePointsCoin.add(coinBasGauche);
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
	 * @return Une chaine indiquant que l'objet est un drapeau
	 */
	// Giroux
	@Override
	public String toString() {
		return "Drapeau " + pointsCoin.toString();
	}

}
