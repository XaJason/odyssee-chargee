package tuile;

import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme un portail
 * 
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 */
public class Portail extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -3752989336876220183L;
	/** l'image représentant un portail */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type portail */
	private static String type = "Portail";
	// Coins du carré//
	/** position du x pour délimiter les points **/
	private double xActuel;
	/** position du y pour délimiter les points **/
	private double yActuel;
	/** Coin haut-droit **/
	private Double coinHautDroit;
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin bas-gauche **/
	private Double coinBasGauche;

	/**
	 * Association du portail
	 */
	private Portail portailAssocie = null;
	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public Portail() {
		super(image, type);
	
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Portail(double angleRotation) {
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
	public Portail(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Retourne l'image représentant le portail
	 * @return L'image représentant le portail
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le portail
	 * 
	 * @param fichierImage Le fichier de l'image représentant le portail
	 * @param largeurTuile La largeur de la tuile
	 * @param hauteurTuile La hauteur de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * 
	 * @return Une chaine indiquant que l'objet est un portail
	 */
	// Giroux
	public String toString() {
		return "Portail ";
	}

	/**
	 * Méthode qui ajoute les coins du carré dans l'arrayList points
	 */
	// Giroux
	public void setPoint() {
		super.setPoint();
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
	}

	/**
	 * Permet d'associer un portail à un autre
	 * @param portailAssocie portail relié à un autre
	 */
	//Kitimir Yim
	public void definirPortailAssocie(Portail portailAssocie) {
		this.portailAssocie = portailAssocie;
	}
	/**
	 * Retourne le portail associé
	 * @return portailAssocie portail relié à un autre
	 */
	//Kitimir Yim
	public Portail getPortailAssocie() {
		return portailAssocie;
	}

	/**
	 * Méthode qui forme le Portail dans un area
	 * @return la forme du Portail dans un area
	 */
	//Kitimir Yim
	public Area formerAireObjetSpecial() {


		Ellipse2D cercle = new Ellipse2D.Double(getPointZero().getX(), getPointZero().getY(), largeurTuile, largeurTuile);

		Area airePortail = new Area(cercle);
		return airePortail;



	}

}
