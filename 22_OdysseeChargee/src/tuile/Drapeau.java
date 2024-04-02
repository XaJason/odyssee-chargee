package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

/**
 * Représente l'objet interactif fixe unique plaçable qui agit comme un drapeau
 * d'arrivée
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class Drapeau extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 6952589919609649643L;
	/** l'image représentant un drapeau */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type drapeau */
	private static String type = "Drapeau";
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
	 * Définit l'image représentant le drapeau d'arrivée
	 * 
	 * @param imageRef l'image représentant drapeau d'arrivée
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		Drapeau.image = imageRef;
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * 
	 * @return Une chaine indiquant que l'objet est un drapeau
	 */
	// Giroux
	public String toString() {
		return "Drapeau " + pointsCoin.toString();
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

}
