package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;

/**
 * Représente l'objet fixe plaçable en forme de triangle rectangle (coin en bas
 * à gauche par défaut)
 *
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 */
public class TriangleRectangle extends Tuile implements Serializable {
	/** l'image représentant un triangle rectangle */
	private static transient Image image;
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 6670803928887029811L;
	/** chaine de caractères représentant la tuile de type triangle rectangle */
	private static String type = "Triangle rectangle";
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
	 * Retourne l'image représentant le triangle rectangle
	 *
	 * @return L'image représentant le triangle rectangle
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le triangle rectangle
	 *
	 * @param fichierImage Le fichier de l'image représentant le triangle rectangle
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
		xActuel = 0;
		yActuel = hauteurTuile;
		prePointsCoin.clear();
		pointsCoin.clear();
		listeSegments.clear();
		pointInitial.setLocation(xActuel, yActuel);
		prePointsCoin.add(pointInitial);
		// Deuxième point(basDroit)
		xActuel += largeurTuile;
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
			i.setLocation(i.getX() + x + largeurTuile / 2, i.getY() + y + hauteurTuile / 2);
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
	 * @return Une chaine indiquant que l'objet est un triangle rectangle
	 */
	// Giroux
	@Override
	public String toString() {
		return "Triangle rectangle ";
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
