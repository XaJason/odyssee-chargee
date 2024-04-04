package tuile;

import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

/**
 * Représente l'objet dynamique plaçable unique agissant comme un vaisseau
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class VaisseauImage extends Tuile implements Serializable {
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
	 * Définit l'image représentant le vaisseau
	 * 
	 * @param imageRef l'image représentant le vaisseau
	 */
	// Jason Xa
	public static void setImageRef(Image imageRef) {
		VaisseauImage.image = imageRef;
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
	 * Méthode qui vérifie si le vaisseau entre en collision avec le drapeau
	 * POUR LE MOMENT EN COMMENTAIRE CAR LA METHODE NE VA PAS LA
	 */
	// Kitimir Yim
	// public boolean verifieCollisionDrapeauVaisseau() {
	// Area vaisseau = VaisseauImage.formeVaisseau();
	// Area drapeau = Drapeau.formeDrapeau();
	// vaisseau.intersect(drapeau);
	// return !vaisseau.isEmpty();

	// }

}
