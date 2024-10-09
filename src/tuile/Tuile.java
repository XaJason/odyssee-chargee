package tuile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import interactif.PlaqueChargee;
import math.MatriceRotation;
import physique.Segment;
import utilitaires.Aire;
import utilitaires.Dessinable;
import utilitaires.Selectionnable;

/**
 * Représente tout objet fixe qui peut être placé dans le mode éditeur.
 *
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 */
public class Tuile /* extends OutilsImage */ implements Dessinable, Serializable, Selectionnable {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -7235372039893162386L;
	/** largeur de la tuile (en mètre) */
	protected static double largeurTuile;
	/** hauteur de la tuile (en mètre) */
	protected static double hauteurTuile;
	/** Largeur d'une demi-tuile **/
	protected static double largeurDemiTuile = largeurTuile / 2.0;
	/** L'abscisse du coin supérieur gauche de la tuile (en mètre) */
	protected double x;
	/** L'ordonnée du coin supérieur gauche de la tuile en (en mètre) */
	protected double y;
	/** l'angle de rotation de la tuile en (rad) */
	protected double angleRotation;

	/** Détermine si la tuile est un drapeau **/
	protected boolean drapeau = false;
	/** Détermine si la tuile est un vaisseau **/
	protected boolean vaisseau = false;

	/** La plaque placée sur la tuile, s'il y a lieu **/
	private PlaqueChargee plaque;

	/** l'image représentant la tuile */
	private transient Image image;
	/** Image redimensionnée de la tuile **/
	protected transient Image imageRedi;
	/** chaine de caractères représentant le type de la tuile */
	protected String type;

	/**
	 * ArrayList qui contient les points des coins des blocs avant d'être transformé
	 **/
	protected ArrayList<Point2D.Double> prePointsCoin = new ArrayList<>();
	/** ArrayList qui contient les points des coins des blocs post-transformé **/
	protected ArrayList<Point2D.Double> pointsCoin = new ArrayList<>();
	/**
	 * ArrayList qui contient les points d'un bloc déja posée
	 */
	protected ArrayList<Point2D.Double> pointsCoinTemp = new ArrayList<>();
	/** Point initial(haut-gauche) du bloc **/
	protected Point2D.Double pointInitial;
	/** Path qui représente le contour du bloc **/
	protected Path2D.Double contour;
	/** Matrice de rotation **/
	protected MatriceRotation rotation;
	/** Point milieu du triangle **/
	protected Point2D.Double pointMilieu;

	/** ArrayList qui contient tous les segments de la tuile **/
	protected ArrayList<Segment> listeSegments = new ArrayList<>();

	/** aires de sélection pour les plaques chargées */
	protected transient Aire[] aires;

	/**
	 * Matrice de rotation utilisée lorsqu'on appuie sur les boutons de rotation
	 **/
	protected MatriceRotation matricePostCreation;

	/** Géométrie de base d'une tuile **/
	private Rectangle2D.Double geometrieDeBase;

	/**
	 * Constructeur
	 *
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 * @param image         l'image représentant la tuile
	 * @param x             l'abscisse gauche de la tuile (en mètre)
	 * @param y             l'ordonnée supérieure la tuile (en mètre)
	 * @param type          le type de la tuile
	 *
	 */
	// Jason Xa
	public Tuile(double angleRotation, double x, double y, String type, Image image) {
		this.angleRotation = angleRotation;
		this.x = x;
		this.y = y;
		this.image = image;
		this.type = type;
		creerLaGeometrie();
	}

	/**
	 * Constructeur
	 *
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 * @param image         l'image représentant la tuile
	 * @param type          le type de la tuile
	 */
	// Jason Xa
	public Tuile(double angleRotation, Image image, String type) {

		this.angleRotation = angleRotation;
		this.image = image;
		this.type = type;
		creerLaGeometrie();
	}

	/**
	 * Constructeur
	 *
	 * @param image l'image représentant la tuile
	 * @param x     l'abscisse gauche de la tuile (en mètre)
	 * @param y     l'ordonnée supérieure la tuile (en mètre)
	 * @param type  le type de la tuile
	 *
	 */
	// Jason Xa
	public Tuile(Image image, int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.type = type;
		creerLaGeometrie();
	}

	/**
	 * Constructeur
	 *
	 * @param image l'image représentant la tuile
	 * @param type  le type de la tuile
	 *
	 */
	// Jason Xa
	public Tuile(Image image, String type) {
		x = 0;
		y = 0;
		this.image = image;
		this.type = type;
		creerLaGeometrie();
	}

	/**
	 * Retourne la hauteur de la tuile
	 *
	 * @return hauteur de la tuile
	 */
	// Kitimir Yim
	public static double getHauteurTuile() {
		return Tuile.hauteurTuile;
	}

	/**
	 * Retourne la largeur de la tuile
	 *
	 * @return la largeur de la tuile
	 */
	// Jason Xa
	public static double getLargeurTuile() {
		return largeurTuile;
	}

	/**
	 * Définit la largeur des tuiles
	 *
	 * @param hauteurTuile la nouvelle hauteur des tuiles (en mètre)
	 *
	 */
	// Jason Xa
	public static void setHauteurTuile(double hauteurTuile) {
		Tuile.hauteurTuile = hauteurTuile;
	}

	/**
	 * Définit la largeur des tuiles
	 *
	 * @param largeurTuile la nouvelle largeur des tuiles (en mètre)
	 */
	// Jason Xa
	public static void setLargeurTuile(double largeurTuile) {
		Tuile.largeurTuile = largeurTuile;
		largeurDemiTuile = largeurTuile / 2.0;
	}

	/**
	 * Retourne vrai si les coordonnées (d'un point) passées en paramètre fontt
	 * partie de l'objet dessinable sur lequel cette méthode est appelée
	 *
	 * @param xPix Coordonnée en x du point (exprimée en pixels)
	 * @param yPix Coordonnée en y du point (exprimée en pixels)
	 * @return vrai si le point fait partie de l'objet dessinable
	 */
	// Jason Xa
	@Override
	public boolean contient(double xPix, double yPix) {
		return contour.contains(xPix, yPix);
	}

	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet
	 * dessinable sur lequel cette méthode est appelée
	 *
	 * @param point le point à vérifier
	 * @return vrai si le point fait est contenu dans l'objet dessinable
	 */
	// Jason Xa
	public boolean contient(Point2D point) {
		return contour.contains(point.getX(), point.getY());
	}

	/**
	 * Créer les aires de sélection associées aux tuiles triangulaires
	 *
	 * @param pointMilieu le point milieu de la tuile carrée
	 */
	// Jason Xa
	public void creerAires(Point2D.Double pointMilieu) {

		Aire aire1 = new Aire(pointsCoin.get(0), pointMilieu, pointsCoin.get(1));
		Aire aire2 = new Aire(pointsCoin.get(1), pointMilieu, pointsCoin.get(2));
		Aire aire3 = new Aire(pointsCoin.get(2), pointMilieu, pointsCoin.get(0));

		aires = new Aire[] { aire1, aire2, aire3 };
	}

	/**
	 * Méthode qui instancie le path qui fait le contour du bloc
	 */
	// Giroux
	public void creerGeometrieContour() {

		contour = new Path2D.Double();
		if (pointsCoin.size() != 0) {
			contour.moveTo(pointsCoin.get(0).getX(), pointsCoin.get(0).getY());
		}
		for (Point2D i : pointsCoin) {
			contour.lineTo(i.getX(), i.getY());
		}
		if (pointsCoin.size() != 0) {
			contour.lineTo(pointsCoin.get(0).getX(), pointsCoin.get(0).getY());
		}
	}

	/**
	 * Crée la géométrie de base d'une tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public void creerLaGeometrie() {
		geometrieDeBase = new Rectangle2D.Double(x, y, largeurTuile, hauteurTuile);
	}

	/**
	 * Méthode qui instancie la liste de segment du bloc Appelé dans la méthode
	 * setPoint des carrés, des triangles rectangles et des triangles équilatéraux
	 */
	// Enuel René Valentin Kizozo Izia
	public void creerListeSegment() {
		if (pointsCoin.size() > 1) {
			for (int i = 0; i < pointsCoin.size() - 1; i++) {
				listeSegments.add(new Segment(pointsCoin.get(i), pointsCoin.get(i + 1)));
			}
			Point2D.Double dernierPoint = pointsCoin.get(pointsCoin.size() - 1);
			listeSegments.add(new Segment(dernierPoint, pointsCoin.get(0)));
		}
	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées dans la grille
	 *
	 * @param g2d Le contexte graphique
	 */
	// Jason Xa
	@Override
	public void dessiner(Graphics2D g2d) {
		AffineTransform transformationAffine = g2d.getTransform();
		g2d.rotate(-angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		setImageRefTuile();
		/*
		 * Ajustement des paramètres pour dessiner l'image à cause des transformations
		 * du paintComponent de Grille permettant de mettre l'origine en bas à gauche
		 */
		double yImage = y + hauteurTuile;
		double hauteurTuileImage = -hauteurTuile;
		g2d.drawImage(image, (int) x, (int) yImage, (int) largeurTuile, (int) hauteurTuileImage, null);
		g2d.setTransform(transformationAffine);

	}

	/**
	 * Dessine l'image représentant la tuile selon les coordonnées passé en
	 * paramètre
	 *
	 * @param g2d contexte graphique
	 * @param x   abscisse gauche de la tuile (en mètre)
	 * @param y   ordonnée supérieure de la tuile (en mètre)
	 */
	// Jason Xa
	public void dessiner(Graphics2D g2d, int x, int y) {
		AffineTransform transformationAffine = g2d.getTransform();
		g2d.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		setImageRefTuile();
		g2d.drawImage(image, x, y, (int) largeurTuile, (int) hauteurTuile, null);
		g2d.setTransform(transformationAffine);
		dessinerContour(g2d);
	}

	/**
	 * Méthode qui forme l'aire d'un objet spécial (pics, drapeau, portail) Redéfini
	 * dans les tuiles qui sont des objets spéciaux
	 *
	 * @return La forme de l'objet spécial dans un Area
	 */
	// Enuel René Valentin Kizozo Izia
	public Area formerAireObjetSpecial() {
		Rectangle2D rectangle = new Rectangle2D.Double(pointInitial.getX(), pointInitial.getY(), largeurTuile,
				hauteurTuile);

		Area aireTuile = new Area(rectangle);
		return aireTuile;
	}

	/**
	 * Retourne les aires séparant la tuile en parties égales
	 *
	 * @return Les aires de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public Aire[] getAires() {
		return aires;
	}

	/**
	 * Retourne l'angle de rotation
	 *
	 * @return the angleRotation l'angle de rotation (rad)
	 */
	// Jason Xa
	public double getAngleRotation() {
		return angleRotation;
	}

	/**
	 * Méthode qui indique si c'est un drapeau
	 *
	 * @return la valeure du boolean drapaeau
	 */
	// Giroux
	public boolean getDrapeau() {
		if (drapeau) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * Retourne la géométrie de base
	 *
	 * @return La géométrie de base
	 */
	// Enuel René Valentin Kizozo Izia
	public Rectangle2D.Double getGeometrieDeBase() {
		return geometrieDeBase;
	}

	/**
	 * Retourne la liste de segment de la tuile
	 *
	 * @return La liste de segment de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public ArrayList<Segment> getListeSegments() {
		return listeSegments;
	}

	/**
	 * Retourne la plaque placée sur la tuile
	 *
	 * @return La plaque placée sur la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public PlaqueChargee getPlaque() {
		return plaque;
	}

	/**
	 * Retourne le point milieu de la tuile
	 *
	 * @return Le point milieu de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public Point2D.Double getPointMilieu() {
		return pointMilieu;
	}

	/**
	 * Retourne la liste de coins de la tuile
	 *
	 * @return La liste de coins de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public ArrayList<Point2D.Double> getPointsCoin() {
		return pointsCoin;
	}

	/**
	 * Retourne le point zéro de la tuile
	 *
	 * @return Le point zéro de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public Point2D.Double getPointZero() {
		return pointsCoin.get(0);
	}

	/**
	 * Retourne la chaine de caractères représentant le type de la tuile
	 *
	 * @return la chaine de caractères représentant le type de la tuile
	 */
	// Jason Xa
	public String getType() {
		return type;
	}

	/**
	 * Méthode qui indique si c'est un vaisseau
	 *
	 * @return la valeure du boolean drapaeau
	 */
	// Giroux
	public boolean getVaisseau() {
		if (vaisseau) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * Retourne l'abscisse gauche de la tuile
	 *
	 * @return L'abscisse gauche de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public double getX() {
		return this.x;
	}

	/**
	 * Retourne l'ordonnée supérieure de la tuile
	 *
	 * @return L'ordonnée supérieure de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public double getY() {
		return this.y;
	}

	/**
	 * Méthode qui calcule le point milieu d'un triangle à l'aide du théoreme de
	 * Thales
	 *
	 * @param sommets Arraylist des sommets du triangles
	 * @return Le point milieu
	 */
	// Giroux
	public Point2D.Double pointMilieuTriangle(ArrayList<Point2D.Double> sommets) {

		// Point 1 va être le sommet, le segment va être entre P2 et P3

		// Trouver le milieu du segment
		double moyenX = sommets.get(2).getX() + (Math.abs(sommets.get(2).getX() - sommets.get(1).getX()) / 2);
		double moyenY = sommets.get(2).getY() + (Math.abs(sommets.get(2).getY() - sommets.get(1).getY()) / 2);
		Point2D.Double milieuSegment = new Point2D.Double(moyenX, moyenY);
		// Trouver le 2/3 de la médiane
		double milieuX = sommets.get(0).getX() + (Math.abs(milieuSegment.getX() - sommets.get(0).getX())) * (2.0 / 3.0);
		double milieuY = sommets.get(0).getY() - (Math.abs(milieuSegment.getY() - sommets.get(0).getY())) * (2.0 / 3.0);
		Point2D.Double milieu = new Point2D.Double(milieuX, milieuY);
		return milieu;
	}

	/**
	 * Méthode qui change la dimension de l'image
	 *
	 * @param hauteurImage l'hauteur voulue de l'image
	 * @param largeurImage la largeur voulue de l'image
	 */
	// Giroux
	public void redimensionnerImage(int hauteurImage, int largeurImage) {
		imageRedi = image.getScaledInstance(largeurImage, hauteurImage, Image.SCALE_DEFAULT);
		setImage(imageRedi);

	}

	/**
	 * Renouvelle la matrice de rotation post-placement
	 */
	// Giroux
	public void rotationPostCreation() {
		matricePostCreation = new MatriceRotation(-this.angleRotation);

	}

	/**
	 * Définit le nouvel angle de rotation de la tuile
	 *
	 * @param d le nouvel angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public void setAngleRotation(double d) {
		this.angleRotation = d;
		creerLaGeometrie();
	}

	/**
	 * Méthode qui met le champ drapeau à vrai
	 */
	// Giroux
	public void setDrapeau() {
		this.drapeau = true;
	}

	/**
	 * Modifie la plaque placée sur la tuile
	 *
	 * @param plaque La plaque placée sur la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPlaque(PlaqueChargee plaque) {
		this.plaque = plaque;
	}

	/**
	 * Méthode à redéfinir dans les sous classes pour mettre les points des coins
	 * dans le arrayList pointsCoin
	 */
	// Giroux
	public void setPoint() {
		pointInitial = new Point2D.Double(0, 0);
		rotation = new MatriceRotation(-this.angleRotation);

		creerLaGeometrie();
	}

	/**
	 * Définit la nouvelle abscisse gauche de la tuile
	 *
	 * @param x la nouvelle abscisse gauche de la tuile (en mètre)
	 */
	// Jason Xa
	public void setX(double x) {
		this.x = x;
		creerLaGeometrie();
	}

	/**
	 * Définit la nouvelle ordonnée supérieure de la tuile
	 *
	 * @param y la nouvelle ordonnée supérieure (en mètre)
	 */
	// Jason Xa
	public void setY(double y) {
		this.y = y;
		creerLaGeometrie();
	}

	/**
	 * Permet d'accéder à une des aires de la tuile (carré ou triangle) où se trouve
	 * le curseur
	 *
	 * @param pointSurvole La position du curseur de la souris qui survole la zone
	 *                     d'animation physique
	 * @return L'une des aires de la tuile, celle où se situe le curseur de la
	 *         souris
	 */
	// Enuel René Valentin Kizozo Izia
	public Aire survolerAiresDeTuile(Point2D pointSurvole) {

		for (Aire aire : aires) {
			if (aire.contient(pointSurvole)) {

				return aire;
			}

		}

		return null;
	}

	/**
	 * Méthode qui met le champ vaisseau à vrai
	 */
	// Giroux
	protected void setVaisseau() {
		this.vaisseau = true;
	}

	/**
	 * Méthode qui dessine la boite de collision
	 *
	 * @param g2d Contexte graphique
	 */
	// Giroux
	private void dessinerContour(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		creerGeometrieContour();
		g2dPrive.setColor(Color.red);
	}

	/**
	 * Méthode qui change l'image actuelle par une nouvelle
	 *
	 * @param nouvImage la nouvelle image
	 */
	// Giroux
	private void setImage(Image nouvImage) {
		this.image = nouvImage;
	}

	/**
	 * Lit, redimensionne et définit l'image pour chaque type de tuile si l'image
	 * n'a pas été chargé Soit, après avoir chargé un fichier binaire sans image
	 */
	// Enuel René Valentin Kizozo Izia
	private void setImageRefTuile() {
		if (image == null) {

			switch (type) {
			case "Carré":
				image = Carre.getImageRef();
				break;
			case "Drapeau":
				image = Drapeau.getImageRef();
				break;
			case "Pics":
				image = Pics.getImageRef();
				break;
			case "Portail":
				image = Portail.getImageRef();
				break;
			case "Triangle équilatéral":
				image = TriangleEquilateral.getImageRef();
				break;
			case "Triangle rectangle":
				image = TriangleRectangle.getImageRef();
				break;
			case "Vaisseau":
				image = VaisseauImage.getImageRef();
				break;
			}
		}
	}
}