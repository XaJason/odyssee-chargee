package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

/**
 * Représente l'objet fixe plaçable en forme de triangle rectangle (coin en bas
 * à gauche par défaut)
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class TriangleRectangle extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 6670803928887029811L;
	/** l'image représentant un triangle rectangle */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type triangle rectangle */
	private static String type = "Triangle rectangle";
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
	public TriangleRectangle() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public TriangleRectangle(double angleRotation) {
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
	public TriangleRectangle(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Définit l'image représentant le triangle rectangle
	 * 
	 * @param imageRef l'image représentant le triangle rectangle
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		TriangleRectangle.image = imageRef;
	}
	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * @return Une chaine indiquant que l'objet est un triangle rectangle
	 */
	// Giroux
	public String toString() {
		return "Triangle rectangle ";
	}
	
	/**
	 * Méthode qui ajoute les coins du triangle dans l'arrayList points
	 */
	//Giroux
	public void setPoint() {
		super.setPoint();
		xActuel=x;
		yActuel=y;
		pointsCoin.add(pointInitial);
		//Deuxième point(basDroit)
		xActuel += largeurTuile;
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
