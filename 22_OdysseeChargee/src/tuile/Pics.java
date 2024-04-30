package tuile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import utilitaires.OutilsImage;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme une plaque de pics
 * (au bas de la tuile par défaut)
 * 
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 */
public class Pics extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 2291455130727306069L;
	/** l'image représentant des pics */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type pics */
	private static String type = "Pics";
	// Coins du rectangle//
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
	public Pics() {
		super(image, type);
	}

	/**
	 * Constructeur
	 * 
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Pics(double angleRotation) {
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
	public Pics(int x, int y) {
		super(image, x, y, type);
	}

	/**
	 * Retourne l'image représentant les pics
	 * 
	 * @return L'image représentant les pics
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant les pics
	 * 
	 * @param fichierImage Le fichier de l'image représentant les pics
	 * @param largeurTuile La largeur de la tuile
	 * @param hauteurTuile La hauteur de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Dessine l'image représentant les pics selon les coordonnées de sa tuile dans la grille
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Jason Xa
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		creerGeometrieContour();
		//g2dPrive.setColor(Color.red);
		// g2d.draw(contour);
		//AffineTransform transformationAffine = g2dPrive.getTransform();
		g2dPrive.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		g2dPrive.drawImage(image, (int) x, (int) (y + largeurTuile / 2.0), (int) largeurTuile, (int) (hauteurTuile / 2.0),
				null);
		//g2dPrive.setTransform(transformationAffine);
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 * 
	 * @return Une chaine indiquant que l'objet est un pic
	 */
	// Giroux
	public String toString() {
		return "Pics " + pointsCoin.toString();
	}

	/**
	 * Méthode qui ajoute les coins du rectangle dans l'arrayList prePointsCoin,
	 * puis qui les transforme avant de les mettre dans pointsCoin
	 */
	// Giroux
	public void setPoint() {
		super.setPoint();
		pointInitial.setLocation(0, hauteurTuile / 2);
		prePointsCoin.add(pointInitial);
		// Deuxième point(HautDroit)
		xActuel = pointInitial.getX() + largeurTuile;
		yActuel = pointInitial.getY();
		coinHautDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(BasDroit)
		yActuel += hauteurTuile / 2;
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
			i.setLocation(i.getX() - largeurTuile / 2, i.getY() - hauteurTuile / 2);
			i = rotation.rotationner(i);
			i.setLocation(i.getX() + largeurTuile / 2 + x, i.getY() + hauteurTuile / 2 + y);
			pointsCoin.add(i);
		}

	}

	/**
	 * Méthode qui forme le pic dans un area
	 * 
	 * @return la forme du pic dans un area
	 */
	// Kitimir Yim
	public Area formerAireObjetSpecial() {

		Area airePics = new Area(contour);
		return airePics;

	}

}
