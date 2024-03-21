package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

/**
 * Représente l'objet fixe plaçable en forme de triangle équilatéral (pointant
 * vers le haut par défaut)
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class TriangleEquilateral extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 2766075546438030099L;
	/** l'image représentant un triangle équilatéral */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type triangle équilatéral */
	private static String type = "Triangle équilatéral";
	//Coins du triangle//
	/**position du x pour délimiter les points**/
	private double xActuel;
	/**position du y pour délimiter les points**/
	private double yActuel;
	/** Coin bas-droit**/
	private Double coinBasDroit;
	/** Coin bas-gauche**/
	private Double coinBasGauche;

	/**
	 * Constructeur
	 * 
	 */
	// Jason Xa
	public TriangleEquilateral() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public TriangleEquilateral(double angleRotation) {
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
	public TriangleEquilateral(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant le triangle équilatéral
	 * 
	 * @param imageRef l'image représentant le triangle équilatéral
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		TriangleEquilateral.image = imageRef;
	}
	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * @return Une chaine indiquant que l'objet est un triangle équilatéral
	 */
	// Giroux
	public String toString() {
		return "Triangle équilatéral ";
	}
	/**
	 * Méthode qui ajoute les coins du triangle dans l'arrayList points
	 */
	//Giroux
	public void setPoint() {
		super.setPoint();
		xActuel=x+largeurTuile/2;
		yActuel=y;
		pointInitial.setLocation(x+largeurTuile/2,y);
		pointsCoin.add(pointInitial);
		//Deuxième point(basDroit)
		xActuel += largeurTuile/2;
		yActuel += hauteurTuile;
		coinBasDroit = new Point2D.Double(xActuel,yActuel);
		//Troisième point(BasGauche)
		xActuel-= largeurTuile;
		coinBasGauche = new Point2D.Double(xActuel,yActuel);
		//Ajouter dans l'arrayList
		pointsCoin.add(coinBasDroit);
		pointsCoin.add(coinBasGauche);
		
		
		
	}

}
