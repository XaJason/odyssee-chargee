package tuile;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;
import utilitaires.Aire;
import utilitaires.Selectionnable;

/**
 * Représente l'objet fixe plaçable en forme de carré
 * 
 * @author Jason Xa
 * @author Giroux
 */
public class Carre extends Tuile implements Serializable, Selectionnable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -5637937761263747229L;
	/** l'image représentant un carré */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type carré */
	private static String type = "Carré";
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
	public Carre() {
		super(image, type);

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
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Carre(double angleRotation) {
		super(angleRotation, image, type);
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
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * 
	 * @return Une chaine indiquant que l'objet est un carré
	 */
	// Giroux
	public String toString() {
		return "Carre" + pointsCoin.toString();

	}

	/**
	 * Méthode qui ajoute les coins du carré dans l'arrayList prePointsCoin, puis
	 * qui les transforme avant de les mettre dans pointsCoin
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

		Point2D.Double pointMilieu = new Point2D.Double(pointInitial.getX() + largeurTuile / 2.0,
				pointInitial.getY() + hauteurTuile / 2.0);

		// Transformer
		for (Point2D.Double i : prePointsCoin) {
			// Prendre le centre
			i.setLocation(i.getX() - largeurTuile / 2, i.getY() - hauteurTuile / 2);
			i = rotation.rotationner(i);
			// Repositionner
			i.setLocation(i.getX() + largeurTuile / 2 + x, i.getY() + hauteurTuile / 2 + y);
			pointsCoin.add(i);
		}

		creerAires(pointMilieu);
	}

	/**
	 * Créer les aires de sélection associées à la tuile carrée(redéfini)
	 * 
	 * @param pointMilieu le point milieu de la tuile carrée
	 */
	// Jason Xa
	protected void creerAires(Point2D.Double pointMilieu) {

		Aire aire1 = new Aire(pointsCoin.get(0), pointMilieu, pointsCoin.get(1));
		Aire aire2 = new Aire(pointsCoin.get(1), pointMilieu, pointsCoin.get(2));
		Aire aire3 = new Aire(pointsCoin.get(2), pointMilieu, pointsCoin.get(3));
		Aire aire4 = new Aire(pointsCoin.get(3), pointMilieu, pointsCoin.get(0));

		aires = new Aire[] { aire1, aire2, aire3, aire4 };
	}

}
