package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;

/**
 * Représente l'objet fixe plaçable en forme de triangle équilatéral (pointant
 * vers le haut par défaut)
 *
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 */
public class TriangleEquilateral extends Tuile implements Serializable {
	/** l'image représentant un triangle équilatéral */
	private static transient Image image;
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 2766075546438030099L;
	/** chaine de caractères représentant la tuile de type triangle équilatéral */
	private static String type = "Triangle équilatéral";
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin bas-gauche **/
	private Double coinBasGauche;

	/** position du x pour délimiter les points **/
	private double xActuel;
	/** position du y pour délimiter les points **/
	private double yActuel;

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
	 * Retourne l'image représentant le triangle équilatéral
	 *
	 * @return L'image représentant le triangle équilatéral
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le triangle équilatéral
	 *
	 * @param fichierImage Le fichier de l'image représentant le triangle
	 *                     équilatéral
	 * @param largeurTuile La largeur de la tuile
	 * @param hauteurTuile La hauteur de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Méthode qui ajoute les coins du triangle dans l'arrayList prePointsCoin, puis
	 * qui les transforme avant de les mettre dans pointsCoin
	 */
	// Giroux
	@Override
	public void setPoint() {
		super.setPoint();
		prePointsCoin.clear();
		pointsCoin.clear();
		xActuel = 0;
		yActuel = 0;
		listeSegments.clear();
		xActuel = largeurTuile / 2;
		yActuel = hauteurTuile;
		pointInitial.setLocation(xActuel, yActuel);
		prePointsCoin.add(pointInitial);
		// Deuxième point(basDroit)
		xActuel += largeurTuile / 2;
		yActuel -= hauteurTuile;
		coinBasDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(BasGauche)
		xActuel -= largeurTuile;
		coinBasGauche = new Point2D.Double(xActuel, yActuel);
		// Ajouter dans l'arrayList
		prePointsCoin.add(coinBasDroit);
		prePointsCoin.add(coinBasGauche);
		// Ajouter le point milieu
		pointMilieu = pointMilieuTriangle(prePointsCoin);
		// Transformer
		for (Point2D.Double i : prePointsCoin) {
			i.setLocation(i.getX() - largeurTuile / 2, i.getY() - hauteurTuile / 2);
			i = rotation.rotationner(i);
			i.setLocation(i.getX() + largeurTuile / 2 + x, i.getY() + hauteurTuile / 2 + y);

			pointsCoin.add(i);
		}
		transformerPointMilieu();
		creerGeometrieContour();
		creerListeSegment();
		creerAires(pointMilieu);
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 *
	 * @return Une chaine indiquant que l'objet est un triangle équilatéral
	 */
	// Giroux
	@Override
	public String toString() {
		return "Triangle équilatéral " + pointsCoin.toString();
	}

	/**
	 * Transformer le pointMilieu (qui ne fait pas partie du contour) selon la
	 * rotation appliquée sur la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	private void transformerPointMilieu() {
		// Prendre le centre
		pointMilieu.setLocation(pointMilieu.getX() - largeurTuile / 2, pointMilieu.getY() - hauteurTuile / 2);
		pointMilieu = rotation.rotationner(pointMilieu);
		// Repositionner
		pointMilieu.setLocation(pointMilieu.getX() + largeurTuile / 2 + x, pointMilieu.getY() + hauteurTuile / 2 + y);
	}
}
