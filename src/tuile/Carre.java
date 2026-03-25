package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.Aire;
import utilitaires.OutilsImage;
import utilitaires.Selectionnable;

/**
 * Représente l'objet fixe plaçable en forme de carré
 *
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 */
public class Carre extends Tuile implements Serializable, Selectionnable {
	/** l'image représentant un carré */
	private static transient Image image;
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -5637937761263747229L;
	/** chaine de caractères représentant la tuile de type carré */
	private static String type = "Carré";
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin haut-droit **/
	private Point2D.Double coinHautDroit;
	/** Coin Haut-gauche **/
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
	public Carre() {
		super(image, type);

	}

	/**
	 * Constructeur
	 *
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Carre(double angleRotation) {
		super(angleRotation, image, type);
	}

	/**
	 * Constructeur
	 *
	 * @param x             l'abscisse gauche de la tuile (px)
	 * @param y             l'ordonnée supérieure la tuile (px)
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Carre(double angleRotation, double x, double y) {
		super(angleRotation, x, y, type, image);
	}

	/**
	 * Constructeur
	 *
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 *
	 */
	// Jason Xa
	public Carre(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Retourne l'image représentant le carré
	 *
	 * @return L'image représentant le carré
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le carré
	 *
	 * @param fichierImage Le fichier de l'image représentant le carré
	 * @param largeurTuile La largeur de la tuile
	 * @param hauteurTuile La hauteur de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Créer les aires de sélection associées à la tuile carrée
	 *
	 * @param pointMilieu le point milieu de la tuile carrée
	 */
	// Jason Xa
	@Override
	public void creerAires(Point2D.Double pointMilieu) {

		Aire aire1 = new Aire(pointsCoin.get(0), pointMilieu, pointsCoin.get(1));
		Aire aire2 = new Aire(pointsCoin.get(1), pointMilieu, pointsCoin.get(2));
		Aire aire3 = new Aire(pointsCoin.get(2), pointMilieu, pointsCoin.get(3));
		Aire aire4 = new Aire(pointsCoin.get(3), pointMilieu, pointsCoin.get(0));

		aires = new Aire[] { aire1, aire2, aire3, aire4 };
	}

	/**
	 * Méthode qui ajoute les coins du carré dans l'arrayList prePointsCoin, puis
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
		prePointsCoin.add(pointInitial);
		// Deuxième point(BasDroit)
		xActuel += largeurTuile;
		coinBasDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(HautDroit)
		yActuel = pointInitial.getY() + hauteurTuile;
		coinHautDroit = new Point2D.Double(xActuel, yActuel);
		// Quatrième point(HautGauche)
		xActuel -= largeurTuile;
		coinHautGauche = new Point2D.Double(xActuel, yActuel);
		// Ajouter dans l'arrayList
		prePointsCoin.add(coinBasDroit);
		prePointsCoin.add(coinHautDroit);
		prePointsCoin.add(coinHautGauche);

		pointMilieu = new Point2D.Double(pointInitial.getX() + largeurTuile / 2.0,
				pointInitial.getY() + hauteurTuile / 2.0);
		// Transformer les points qui sont au coin
		for (Point2D.Double i : prePointsCoin) {
			// Prendre le centre
			i.setLocation(i.getX() - largeurTuile / 2, i.getY() - hauteurTuile / 2);
			i = rotation.rotationner(i);
			// Repositionner
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
	 * @return Une chaine indiquant que l'objet est un carré
	 */
	// Giroux
	@Override
	public String toString() {
		return "Carre" + pointsCoin.toString();

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
